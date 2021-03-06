@prefix : <${baseURI}> .
@prefix swc: <http://data.semanticweb.org/ns/swc/ontology#> . 
@prefix swrc: <http://swrc.ontoware.org/ontology#> . 
@prefix map: <file:/Users/andrea/Desktop/${confAcronym?upper_case}${year}data/D2RQ/mapping-${confAcronym?lower_case}.ttl#> .
@prefix foaf: <http://xmlns.com/foaf/0.1/> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix owl: <http://www.w3.org/2002/07/owl#> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
@prefix d2rq: <http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#> .
@prefix dc: <http://purl.org/dc/elements/1.1/> .
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix skos: <http://www.w3.org/2004/02/skos/core#> .
@prefix vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> .
@prefix jdbc: <http://d2rq.org/terms/jdbc/> .
@prefix icaltzd: <http://www.w3.org/2002/12/cal/icaltzd#> .

# Keynotes 

map:Keynote a d2rq:ClassMap;
	d2rq:dataStorage map:database;
	d2rq:uriPattern "${baseURI}conference/${confAcronym?lower_case}/${year}/keynote/@@KEYNOTE.id@@";
	d2rq:class swc:KeynoteEvent .
	
map:keynote_vevent a d2rq:PropertyBridge;
	d2rq:belongsToClassMap map:Keynote;
	d2rq:property rdf:type;
	d2rq:constantValue <http://www.w3.org/2002/12/cal/icaltzd#Vevent> .
	
map:keynote_start a d2rq:PropertyBridge;
	d2rq:belongsToClassMap map:Keynote;
	d2rq:property icaltzd:dtstart;
	d2rq:pattern "@@KEYNOTE.date@@T@@KEYNOTE.start@@" .
	
map:keynote_end a d2rq:PropertyBridge;
	d2rq:belongsToClassMap map:Keynote;
	d2rq:property icaltzd:dtend;
	d2rq:pattern "@@KEYNOTE.date@@T@@KEYNOTE.end@@" .