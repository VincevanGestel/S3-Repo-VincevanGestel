<template>
  <div class="chat">
    <h2>Chat</h2>
    <div class="messages">
      <div v-for="(msg, index) in messages" :key="index" class="message">
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
      // Change this to your backend WebSocket URL
      const socket = new SockJS('http://localhost:8080/ws'); // Adjust if needed
      this.client = new Client({
        webSocketFactory: () => socket,
        debug: (str) => console.log(str),
        reconnectDelay: 5000,
      });

      this.client.onConnect = () => {
        console.log('Connected to WebSocket');

        // Subscribe to message topic
        this.client.subscribe('/topic/messages', (msg) => {
          const body = JSON.parse(msg.body);
          this.messages.push(body);
        });

        // Send join message with username
        this.client.publish({
          destination: '/app/join',
          body: JSON.stringify({ username: this.username, password: '' }),
        });
      };

      this.client.activate();
    },
    sendMessage() {
      if (!this.message.trim()) return;
      const chatMessage = {
        sender: this.username,
        content: this.message.trim(),
      };
      this.client.publish({
        destination: '/app/chat',
        body: JSON.stringify(chatMessage),
      });
      this.message = '';
    },
    formatTimestamp(timestamp) {
      if (!timestamp) return '';
      return new Date(timestamp).toLocaleTimeString();
    },
  },
  mounted() {
    // Ask for username or get from your app state
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
}
.message {
  margin-bottom: 0.5rem;
}
.timestamp {
  font-size: 0.7rem;
  color: gray;
  margin-left: 0.5rem;
}
</style>
