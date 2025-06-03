describe('Login flow with HTTP Basic Auth', () => {
  beforeEach(() => {
    cy.clearCookies();
    cy.clearLocalStorage();
  });

  it('logs in as user and accesses protected page', () => {
    cy.visit('/productlist', {
      auth: {
        username: 'user',
        password: 'user',
      },
    });

    cy.contains('Logged in as user').should('be.visible');
  });
});
