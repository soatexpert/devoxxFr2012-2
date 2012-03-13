/*
  Simple OpenID Plugin
  http://code.google.com/p/openid-selector/
  French translation made by Olivier Pons / 2011/01/10
  http://olivierpons.fr/
  This code is licensed under the New BSD License.
*/

var providers_large = {
  google : {
    name : 'Google',
    url : 'https://www.google.com/accounts/o8/id'
  },
  yahoo : {
    name : 'Yahoo',
    url : 'http://me.yahoo.com/'
  },  
  myopenid : {
    name : 'MyOpenID',
    label : 'Entrez votre nom d\'utilisateur MyOpenID.',
    url : 'http://{username}.myopenid.com/'
  },
  openid : {
    name : 'OpenID',
    label : 'Entrez votre OpenID.',
    url : null
  },
  aol : {
    name : 'AOL',
    label : 'Entrez votre nom d\'écran AOL.',
    url : 'http://openid.aol.com/{username}'
  }
};

var providers_small = {
  
};

openid.locale = 'fr';
openid.sprite = 'en'; // reused in german & japan localization
openid.demo_text = 'Client en mode démonstration. Normalement il faudrait envoyer l\'OpenID:';
openid.signin_text = 'Sign-In';
openid.image_title = 'Connexion avec {provider}';
