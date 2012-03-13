/*
	Simple OpenID Plugin
	http://code.google.com/p/openid-selector/
	
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
		label : 'Bitte den MyOpenID Benutzernamen eingeben.',
		url : 'http://{username}.myopenid.com/'
	},
	openid : {
		name : 'OpenID',
		label : 'Bitte OpenID eingeben.',
		url : null
	},
	aol : {
		name : 'AOL',
		label : 'Bitte den AOL Benutzernamen eingeben.',
		url : 'http://openid.aol.com/{username}'
	}
};

var providers_small = {
};

openid.locale = 'de';
openid.sprite = 'en'; // use same sprite as english localization
openid.demo_text = 'Demo Modus. Normalerweise würde die folgende OpenID übermittelt werden:';
openid.signin_text = 'Anmelden';
openid.image_title = 'Mit {provider} anmelden';
