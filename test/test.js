var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../server.js');
var should = chai.should();

chai.use(chaiHttp);

describe('Hello Test', function() {
 	it('Test GET /api/v1/hello', function(done) {
 		chai.request(server)
 			.get('/api/v1/hello')
 			.end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('array');
				 done();
 			});
 	});
});

describe('Goodbye Test', function() {
    it('Test GET /api/v1/goodbye', function(done) {
        chai.request(server)
            .get('/api/v1/goodbye')
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('array');
                done();
            });
    });
});

describe('films Test', function() {
    it('Test GET /api/v1/films', function(done) {
        chai.request(server)
            .get('/api/v1/films')
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('array');
                done();
            });
    });
});

describe('Film Select Test', function() {
    it('Test GET /api/v1/films?limit=1&offset=0', function(done) {
        chai.request(server)
            .get('/api/v1/films?limit=1&offset=0')
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('object');

                res.body.should.have.property('film_id');
                res.body.should.have.property('title');
                res.body.should.have.property('description');
                res.body.should.have.property('release_year');
                res.body.should.have.property('language_id');
                res.body.should.have.property('original_language_id');
                res.body.should.have.property('rental_duration');
                res.body.should.have.property('rental_rate');
                res.body.should.have.property('length');
                res.body.should.have.property('replacement_cost');
                res.body.should.have.property('rating');
                res.body.should.have.property('special_features');
                res.body.should.have.property('last_update');

                res.body.film_id.should.equal('1');
                res.body.title.should.equal('ACADEMY DINOSAUR');
                res.body.description.should.equal("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies");
                res.body.release_year.should.equal('2006');
                res.body.language_id.should.equal('1');
                res.body.original_language_id.should.equal(null);
                res.body.rental_duration.should.equal(6);
                res.body.rental_rate.should.equal(0.99);
                res.body.length.should.equal(86);
                res.body.replacement_cost.should.equal(20.99);
                res.body.rating.should.equal("PG");
                res.body.special_features.should.equal('Deleted Scenes,Behind the Scenes');
                res.body.special_features.should.equal('2006-02-15T04:03:42.000Z');
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