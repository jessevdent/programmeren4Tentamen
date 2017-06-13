var express = require('express');
var routes = express.Router();
var db = require('../config/db');

routes.get('/hello', function(req, res){
	res.contentType('application/json');
	res.status(200);
	res.json({ 'tekst': 'Hello!'});
});

routes.get('/goodbye', function(req, res){
	res.contentType('application/json');
	res.status(200);
	res.json({ 'tekst': 'Goodbye!'});
});

module.exports = routes;