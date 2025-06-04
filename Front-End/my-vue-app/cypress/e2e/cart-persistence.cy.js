describe('Cart Persistence', () => {
  it('retains items in cart after reload', () => {
    cy.visit('/productlist', {
      auth: { username: 'user', password: 'user' }
    });

    cy.get('ul > li').first().within(() => {
      cy.contains('Add to Cart').click();
    });

    cy.visit('/cart', {
      auth: { username: 'user', password: 'user' }
    });

    cy.reload();
    cy.get('ul > li').should('have.length.at.least', 1);
  });
});
