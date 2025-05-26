import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import CreateProduct from '@/components/CreateProduct.vue'

// Mock the API module
vi.mock('@/services/api', () => ({
  getAllTags: vi.fn(() => Promise.resolve([
    { id: 1, name: 'Tag A' },
    { id: 2, name: 'Tag B' },
  ])),
  createProduct: vi.fn(() => Promise.resolve({ id: 123 })),
}))

// Mock router
const mockPush = vi.fn()

const mountComponent = () => {
  return mount(CreateProduct, {
    global: {
      mocks: {
        $router: {
          push: mockPush,
        },
      },
    },
  })
}

describe('CreateProduct.vue', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders form and loads tags', async () => {
    const wrapper = mountComponent()
    await flushPromises()

    expect(wrapper.find('h1').text()).toBe('Create Product')
    expect(wrapper.findAll('option')).toHaveLength(2)
    expect(wrapper.text()).toContain('Tag A')
    expect(wrapper.text()).toContain('Tag B')
  })

  it('submits form and calls createProduct', async () => {
    const wrapper = mountComponent()
    await flushPromises()

    // Fill in form
    await wrapper.find('#name').setValue('Test Product')
    await wrapper.find('#price').setValue('99.99')
    await wrapper.find('#tags').setValue(['1', '2'])

    // Submit form
    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    const { createProduct } = await import('@/services/api')
    expect(createProduct).toHaveBeenCalledWith({
      name: 'Test Product',
      price: 99.99,
      tagIds: [1, 2],
    })

    expect(mockPush).toHaveBeenCalledWith('/')
  })
})
