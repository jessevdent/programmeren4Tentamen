var express = require('express');
var routes = express.Router();
var db = require('../config/db');
var auth = require('../auth/authentication');

routes.post('/login', function(req, res) {

    console.dir(req.body);

    var username = req.body.username;
    var password = req.body.password;

    var query = {
        sql: 'SELECT username, password , customer_id FROM customer WHERE username = ? AND password = ?',
        values: [username, password],
        timeout: 2000
    };
   
   db.query(query, function(error, rows, fields)
   {
   	if (rows[0].username == username && rows[0].password == password) {
        var token = auth.encodeToken(username);
        res.status(200).json({
            "token": token,
        });
    } else {
        console.log('Input: username = ' + username + ', password = ' + password);
        res.status(401).json({ "error": "Invalid credentials, bye" })
   		}
	});
});

routes.post('/register', function(req, res) {

    var users = req.body;
    var query = {
        sql: 'INSERT INTO `1033`.`customer`(`create_date`, `last_update`, `username`, `password`, `first_name`, `last_name`, `email`, `address`, `postal_code`, `city`, `country`, `house_number`)VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)',
        values: [users.username, users.password, users.first_name, users.last_name, users.email, users.address, users.postal_code, users.city, users.country, users.house_number],
        timeout: 2000
    };

    console.dir(users);
    console.log('Onze query: ' + query.sql);

    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

module.exports = routes;