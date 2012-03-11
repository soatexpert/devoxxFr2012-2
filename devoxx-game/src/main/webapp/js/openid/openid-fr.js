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
  aol : {
    name : 'AOL',
    label : 'Entrez votre nom d\'écran AOL.',
    url : 'http://openid.aol.com/{username}'
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
  }
};

var providers_small = {
  livejournal : {
    name : 'LiveJournal',
    label : 'Entrez votre nom d\'utilisateur Livejournal.',
    url : 'http://{username}.livejournal.com/'
  },
  /*
  flickr: {
    name: 'Flickr',
    label : 'Entrez votre nom d\'utilisateur Flickr.',
    url: 'http://flickr.com/{username}/'
  },
  technorati: {
    name: 'Technorati',
    label : 'Entrez votre nom d\'utilisateur Technorati.',
    url: 'http://technorati.com/people/technorati/{username}/'
  },
  */
  wordpress : {
    name : 'Wordpress',
    label : 'Entrez votre nom d\'utilisateur Wordpress.',
    url : 'http://{username}.wordpress.com/'
  },
  blogger : {
    name : 'Blogger',
    label : 'Entrez votre compte Blogger',
    label : 'Your Blogger account',
    url : 'http://{username}.blogspot.com/'
  },
  verisign : {
    name : 'Verisign',
    label : 'Entrez votre nom d\'utilisateur Verisign',
    url : 'http://{username}.pip.verisignlabs.com/'
  },
  /* vidoop: {
    name: 'Vidoop',
    label : 'Entrez votre nom d\'utilisateur Vidoop',
    url: 'http://{username}.myvidoop.com/'
  }, */
  /* launchpad: {
    name: 'Launchpad',
    label : 'Entrez votre nom d\'utilisateur Launchpad',
    url: 'https://launchpad.net/~{username}'
  }, */
  claimid : {
    name : 'ClaimID',
    label : 'Entrez votre nom d\'utilisateur ClaimID',
    url : 'http://claimid.com/{username}'
  },
  clickpass : {
    name : 'ClickPass',
    label : 'Entrez votre nom d\'utilisateur ClickPass',
    url : 'http://clickpass.com/public/{username}'
  },
  google_profile : {
    name : 'Google Profile',
    label : 'Entrez votre nom de Profil Google',
    url : 'http://www.google.com/profiles/{username}'
  }
};

openid.locale = 'fr';
openid.sprite = 'en'; // reused in german & japan localization
openid.demo_text = 'Client en mode démonstration. Normalement il faudrait envoyer l\'OpenID:';
openid.signin_text = 'Sign-In';
openid.image_title = 'Connexion avec {provider}';
