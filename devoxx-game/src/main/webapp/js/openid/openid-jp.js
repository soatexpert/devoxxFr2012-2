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
		label : 'MyOpenIDのユーザーネームを記入してください。',
		url : 'http://{username}.myopenid.com/'
	},
	openid : {
		name : 'OpenID',
		label : 'OpenIDを記入してください。',
		url : null
	},
	aol : {
		name : 'AOL',
		label : 'AOLのスクリーンネームを記入してください。',
		url : 'http://openid.aol.com/{username}'
	},
};

var providers_small = {	
};

openid.locale = 'jp';
openid.sprite = 'en'; // use same sprite as english localization
openid.demo_text = '今クライアントデモモードになっています。普通は次のOpenIDを出さなければいけません:';
openid.signin_text = 'ログイン';
openid.image_title = '{provider}でログイン';
