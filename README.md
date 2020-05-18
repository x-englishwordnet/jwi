<p align="center">
<img width="256" height="256" src="images/xewn2.png">
</p>
<p align="center">
<img width="256" src="images/mavencentral.png">
</p>

# JWI Java Wordnet Interface

MIT's Java WordNet Interface [upstream](https://projects.csail.mit.edu/jwi/).

Rewritten to **Java 8** with lambda expressions.

Global LexID check can be disabled.

Set global hints capability.

Set dictionary resource matcher (eg can use one index amongst many).

Set dictionary comparator (eg use a different comparator for index file).

Factored out IContentType key functionality into ContentType key enum.

Extensive JUnit testing (see test classes).

Added Maven support.
    
GroupID and ArtifactID on Maven Central:
	
	<groupId>io.github.x-englishwordnet</groupId>
	<artifactId>jwix</artifactId>
	<packaging>jar</packaging>
	<version>2.4.0.1</version>