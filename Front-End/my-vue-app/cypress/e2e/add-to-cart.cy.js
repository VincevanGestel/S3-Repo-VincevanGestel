describe('Add Product to Cart E2E', () => {
  beforeEach(() => {
    cy.clearCookies();
    cy.clearLocalStorage();

    // Intercept console errors
    cy.on('window:before:load', (win) => {
      cy.stub(win.console, 'error').as('consoleError');
    });

    cy.visit('/productlist', {
      auth: {
        username: 'user',
        password: 'user',
      },
    });

    cy.intercept('POST', '**/api/cart/add**').as('addToCart');
  });

  it('adds a product to the cart and verifies it without console errors', () => {
    cy.get('ul > li').first().within(() => {
      cy.contains('Add to Cart').click();
    });

    cy.wait('@addToCart').its('response.statusCode').should('eq', 200);

    cy.visit('/cart', {
      auth: {
        username: 'user',
        password: 'user',
      },
    });

    cy.get('ul > li').should('have.length.at.least', 1);
    cy.contains('Total:').should('exist');

    // ✅ Assert no console errors were logged
    cy.get('@consoleError').should('not.have.been.called');
  });
});
