describe('Add Product to Cart E2E', () => {
  beforeEach(() => {
    cy.clearCookies();
    cy.clearLocalStorage();

    cy.visit('/productlist', {
      auth: {
        username: 'user',
        password: 'user',
      },
    });

    // Adjust intercept to match the actual API call path
    //cy.intercept('POST', '**/cart/add**').as('addToCart');
    // If your axios base URL includes `/api`, use this instead:
     cy.intercept('POST', '**/api/cart/add**').as('addToCart');
  });

  it('adds a product to the cart and verifies it', () => {
    cy.get('ul > li').first().within(() => {
      cy.contains('Add to Cart').click();
    });

    // Wait for the addToCart request to complete
    cy.wait('@addToCart').its('response.statusCode').should('eq', 200);

    cy.visit('/cart', {
      auth: {
        username: 'user',
        password: 'user',
      },
    });

    cy.get('ul > li').should('have.length.at.least', 1);
    cy.contains('Total:').should('exist');
  });
});
