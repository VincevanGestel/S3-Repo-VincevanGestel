describe('Cart Persistence', () => {
  beforeEach(() => {
    // Intercept console errors before page loads
    cy.on('window:before:load', (win) => {
      cy.stub(win.console, 'error').as('consoleError');
    });
  });

  it('retains items in cart after reload without console errors', () => {
    cy.visit('/productlist', {
      auth: { username: 'user', password: 'user' },
    });

    cy.get('ul > li').first().within(() => {
      cy.contains('Add to Cart').click();
    });

    cy.visit('/cart', {
      auth: { username: 'user', password: 'user' },
    });

    cy.reload();

    cy.get('ul > li').should('have.length.at.least', 1);

    cy.get('@consoleError').should('not.have.been.called');
  });
});
