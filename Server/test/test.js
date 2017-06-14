var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../server.js');
var should = chai.should();
var expectedlanguage = ['Mandarin','German','English'];

chai.use(chaiHttp);

describe('API Test', function() {
 	it('Test GET /api/v1/database', function(done) {
 		chai.request(server)
 			.get('/api/v1/database')
 			.end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('array');
				 done();
 			});
 	});
});

describe('INFO Test', function() {
	 it('Test GET /api/v1/info', function(done) {
 		chai.request(server)
			 .get('/api/v1/info')
			 .end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('object');
				 done();
 			});
 	});
});

describe('NUMBER Test', function() {
	 it('Test GET /api/v1/database/2', function(done) {
 		chai.request(server)
			 .get('/api/v1/database/2')
			 .end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('object');
				 res.body.should.have.property('firstname');
                 res.body.should.have.property('lastname');
                 res.body.should.have.property('languages');
                 res.body.should.have.property('expertise');
                 res.body.firstname.should.equal('Chen');
                 res.body.lastname.should.equal('Wang');
                 res.body.languages.should.equal(expectedlanguage);
                 res.body.expertise.should.equal('Technical');
				 done();
 			});
 	});
});

describe('FNAME Test', function() {
	 it('Test GET /api/v1/database?firstname=Kees', function(done) {
 		chai.request(server)
			 .get('/api/v1/database?firstname=Kees')
			 .end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('array');
				 done();
 			});
 	});
});

describe('LNAME Test', function() {
	 it('Test GET /api/v1/database?lastname=Wang', function(done) {
 		chai.request(server)
			 .get('/api/v1/database?lastname=Wang')
			 .end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('array');
				 done();
 			});
 	});
});