XML and SOAP Play library
=========================
[![Build Status](https://travis-ci.org/giabao/scala-soap.svg)](https://travis-ci.org/giabao/scala-soap)

XML and SOAP Play library, for Scala to serialize/deserialize XML (and SOAP) without code generation or other magic.

You may use this api if like me:
- You don't like SOAP because it's not human-friendly at all, it's too verbose and the surrounding standards are just non-sense
- You hate WSDL because it's not humanly readable and requires tools to manipulate them
- You must pollute your code with esoteric annotations just to do RPC in the great majority of cases 
- You hate all those bloated frameworks to manage SOAP and the crazy standards around it
- You don't understand why we still use SOAP but you still have to communicate in SOAP in many cases so you still must bear it
- You just want to extract the data you need from SOAP frames you receive and to generate SOAP frames that look like what your client expect but you don't care about the standards behind that.

**This API is just a set of tools, almost only syntactic sugar to help you**:
- It DOES NOT pretend to provide pure standard SOAP.
- It DOES NOT generate WSDL so you must provide it yourself.
- It just helps you mimic SOAP by providing a few tools and helpers to deserialize/serialize.
- It just aims at being practical without needing deep knowledge of SOAP standards.
- It can serialize/deserialize SOAP so it can also do it for XML...
- It uses pure Scala XML library even if it's a bit incoherent sometimes. AntiXML could be cool too...

More information on https://github.com/giabao/scala-soap

### Install
add to build.sbt:
`libraryDependencies += "com.sandinh" %% "scala-soap" % "1.2.0"`

### Changelogs
see [CHANGES.md](CHANGES.md)

### Licence
This software is licensed under the Apache 2 license:
http://www.apache.org/licenses/LICENSE-2.0

Credits to Pascal Voitot and Paweł Prażak for previous version of this codebase

Credits to Étienne Vallette d'Osia for inspiring/tackling draft version of this code ;)
