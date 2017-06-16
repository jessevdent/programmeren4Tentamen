var http = require('http');
var express = require('express');
var bodyParser = require('body-parser')
var logger = require('morgan');
var routes_v1 = require('./api/routes_v1');
var auth_routes = require('./api/auth.routes');
var config = require('./config/config');
var db = require('./config/db');
var expressJWT = require('express-jwt');

var app = express();

app.use(bodyParser.urlencoded({'extended':'true'}));
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/vnd.api+json' }));
app.use(expressJWT({ secret : config.secretkey })
	.unless({
		path: ['/api/v1/login', '/api/v1/register', /^\/api\/v1\/film\/.*\/.*/  ,/^\/api\/v1\/films\/.*/]
	}));

app.set('port', (process.env.PORT | config.webPort));
app.set('env', (process.env.ENV | 'development'))

app.use(logger('dev'));

app.use('/api/v1', routes_v1);
app.use('/api/v1', auth_routes);

app.use('*', function(req, res){
	res.status(400);
	res.json({
		'error' : 'Deze URL is niet beschikbaar.'
	});
});

app.listen(process.env.PORT || 3000, function(){
	console.log('De server luistert op port ' + app.get('port'));	
});

module.exports = app;
