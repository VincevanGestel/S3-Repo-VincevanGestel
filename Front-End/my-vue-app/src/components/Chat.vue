<template>
  <div class="chat">
    <h2>Chat</h2>

    <div class="messages">
      <div
        v-for="(msg, index) in messages"
        :key="index"
        class="message"
      >
        <strong>{{ msg.sender }}:</strong> {{ msg.content }}
        <small class="timestamp">{{ formatTimestamp(msg.timestamp) }}</small>
      </div>
    </div>

    <input
      v-model="message"
      @keyup.enter="sendMessage"
      placeholder="Type your message here..."
    />
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script>
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export default {
  name: 'Chat',
  data() {
    return {
      client: null,
      messages: [],
      message: '',
      username: null,
    };
  },
  methods: {
    connect() {
      const socket = new SockJS('http://localhost:8080/ws');
      this.client = new Client({
        webSocketFactory: () => socket,
        debug: (str) => console.log('STOMP Debug:', str),
        reconnectDelay: 5000,
      });

      this.client.onConnect = () => {
        console.log('✅ Connected to WebSocket server');

        this.client.subscribe('/topic/messages', (msg) => {
          console.log('📩 Received raw message:', msg.body);
          try {
            const body = JSON.parse(msg.body);
            this.messages.push(body);
          } catch (err) {
            console.error('❌ Failed to parse message:', err);
          }
        });

        // Send join info
        this.client.publish({
          destination: '/app/join',
          body: JSON.stringify({ username: this.username, password: '' }),
        });
      };

      this.client.onStompError = (frame) => {
        console.error('STOMP Error:', frame);
      };

      this.client.activate();
    },
    sendMessage() {
      if (!this.message.trim()) return;
      const chatMessage = {
        sender: this.username,
        content: this.message.trim(),
      };
      console.log('📤 Sending message:', chatMessage);
      this.client.publish({
        destination: '/app/chat',
        body: JSON.stringify(chatMessage),
      });
      this.message = '';
    },
    formatTimestamp(timestamp) {
      if (!timestamp) return '';
      const date = new Date(timestamp);
      if (isNaN(date.getTime())) {
        return '(invalid time)';
      }
      return date.toLocaleTimeString();
    },
  },
  mounted() {
    this.username = prompt('Enter your username') || 'Anonymous';
    this.connect();
  },
  beforeUnmount() {
    if (this.client) {
      this.client.deactivate();
    }
  },
};
</script>

<style scoped>
.chat {
  border: 1px solid #ccc;
  padding: 1rem;
  max-width: 400px;
}
.messages {
  height: 300px;
  overflow-y: auto;
  border: 1px solid #ddd;
  margin-bottom: 1rem;
  padding: 0.5rem;
  background: #f9f9f9;
}
.message {
  margin-bottom: 0.5rem;
  line-height: 1.3;
}
.timestamp {
  font-size: 0.7rem;
  color: gray;
  margin-left: 0.5rem;
}
input {
  width: calc(100% - 60px);
  padding: 0.5rem;
  box-sizing: border-box;
}
button {
  padding: 0.5rem 1rem;
  margin-left: 0.5rem;
}
</style>
