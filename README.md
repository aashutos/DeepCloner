# DeepCloner
A lightweight library used to deep clone objects in java.

# How it works?

Deep Cloner is a very simple piece of code, which modularises and decouples cloning functionality and improves singular responsibility by using a dedicated `DeepCloner` class. Some key type implementations have been developed and defined in the package such as Integer, Double and List. The Cloning rules tend to use Java Reflection. The Clone Rules interface is extensible and flexible to facilitate custom object and POJO duplication.

# How to integrate library into your code?

The code cannot be pulled from any public Maven Repository. However, this source is freely available on GitHub and can be cloned and built.

### Maven build

The library can be built using the following command:

```bash
$MAVEN_HOME/bin/mvn clean install -f {Project Root Directory}/pom.xml
```

### Maven example dependency definition

The maven dependency can be integrated into your pom using the following dependency xml with the appropriate version specified:

e.g.
```xml
<dependency>
  <groupId>com.ntak</groupId>
  <artifactId>deepcloner</artifactId>
  <version>0.0.0.1</version>
</dependency>
```

### Gradle example dependency definition

It can also be integrated into gradle as follows:

e.g.
```groovy
compile group: 'com.ntak', name: 'deepcloner', version: '0.0.0.1'
```

### Standard Deep Cloner contexts

I have included utility classes to help with the cloning of many standard types. Within the `DeepClonerImpls` class there are some static definitions:

```java
public static final DeepCloner IMMUTABLE_PASS_STRING_CLONER;
```

`IMMUTABLE_PASS_STRING_CLONER` cloner will not duplicate String types and will reuse values from the String pool. It will also include the other (Non String) standard types that are specified in the below section. These cloners are immutable definitions and so are not designed to be changed within your program. Trying to add rules to these implementations will yield an _UnsupportOperationException_.

Instances of rules will need to be generated on a per Deep Cloner context basis and cannot be shared. The contexts are set when rules are added to a Deep Cloner context. In the future, a _CloneRuleFactory_ could be used to generate rules instances.

A _CloneRuleFactory_ has been provided as a means to generate Clone Rules. An amalgamating factory can be used to provide a sort of Chain of Responsbility approach to generating Clone Rules by visiting each factory sequentially. This can aid in decorating further custom clone rule factory functionality to the existing _StandardCloneRuleFactory_. This factory is passed in as a field for the DeepCloner as a means to create instances of CloneRule for that specific instance. This allows for the generation of multiple DeepCloner instances. The immutable Deep Cloner instance will be decoupled from the Deep Cloner instance and so will have it own set of rule instances.

### Supported Cloning types

The following types can be cloned:

Collections:
* Array Immutable List
* List
* Map
* Queue
* Set

Complex structures/ Collection extensions:
*	Properties

Base Types:
* BigDecimal
* BigInteger
* Boolean
* Byte
* Character
* Double
* Float
* Integer
* Long
* Short
* String:
  * Pool Based "Non clone" clone: No cloning, but reutilisation of String pool values
  * Non Pool Based clone: Cloning occurs, but new String will not feature in String pool

### Creating Clone Rules for custom types

Custom clone rules can be implemented by using the CloneRule interface:

```java
public interface CloneRule<K> {

	public void setRuleContext(DeepCloner context); // For any nested evaluation of cloning process
	public boolean isInstanceOf(Object object);
	public K clone(Object object);

}
```

The above code has three methods:
* _setRuleContext()_: For nested cloning of complex types, the context is passed around to enable the usage of other clone rules in cloning the complex type.
* _isInstanceOf()_: DeepCloner performs a validation step to check to see whether _clone()_ can be attempted with this rule.
* _clone()_: Attempts to clone of the input object.

I have two further abstract Clone Rule classes, which include a predefined _Rule Context_ implementation for convenience:
* `PrimitiveCloneRule`: No Context is required as this is a terminating clone process.
* `CombinatoryCloneRule`: Context is required as there are sub-types that need to be parsed.

# Issues with code

If there are any bugs that you have identified within this library then please raise a Bug Report using the following url: https://github.com/aashutos/DeepCloner/issues/new?template=bug_report.md

# New features

If you feel that there are any particular features that are worth implementing then please add a Feature Request ticket at: https://github.com/aashutos/DeepCloner/issues/new?template=feature_request.md

# Contributing

Feel free to clone and branch this project for your own use. This project uses the MIT license and so the LICENSE file will need to be kept with the source.

This project can be cloned via the following command:

```bash
git clone https://github.com/aashutos/DeepCloner.git {target directory}
```

# Feedback

If you have any other comments please feel free to drop a comment on my website: http://www.92ak.co.uk or email me at: akakshepati@gmail.com
