

# Database & Storage Systems

## Relational Database

İlişkisel veri tabanları veri güvenirliliğini ön planda tutan ACID prensiplerini göz önünde bulundurarak tasarlanmış databaselerdir.

Veriler normalize edilmiş bir şekilde tablo yapılarında tutulurlar. Bu şekilde diskte optimize edilmiş bir hacimde verileri tutarlar.

Birbirlerine sıkı bir şekilde bağlanmış ilişkisel veriler tutabilirler. (primary key, foreign key yapıları ile)

Distrubuted bir şekilde horizontal olarak scale etmeye uygun değiller.

**Atomicity**: Bir transactionın parçalanamaz biçimde ya tamamen tamamlanması yada tamamen fail olması ve transctionda ki işlemlerin geri alınmasıdır(roll back).

**Consistency:** Herhangi bir T anında yapılan bir okuma işleminin herkes tarafından aynı olarak okunmasıdır.

**Isolation:** Bir transaction bitene kadar yapılan işlemlerin diğer transaction'ları etkilememesidir. Bunu başlayan bir transaction bitene kadar diğer transaction'ları bekleterek sağlar.

**Durability:** Eğer bir transaction commit edilmişse onun kaybolmaması demektir.

## NoSql Databases

NoSQL database'ler genellikle distrubuted bir şekilde horizontal olarak scale edilebilmek üzerine tasarlanmış databaselerdir.

Document based, key value based gibi tipleri var. Veriler birbirleri ile ilişkili olmadıkları için farklı sunucularda tutulmaya uygunlar.

Örnek use case:

Diyelim ki MongoDB kullanıyorsunuz. Sizin kitaplar diye bir döküman tipiniz var. Bu kitap'ların büyüklüğüde 50 GB diyelim. Yani bir ilişkisel db'de tutmak isteseniz bir sunucu'da 50GB lık bir tabloda tutacaksın.

Bir sorgu attığın zaman gidip 50 GB data üzerinde scan etmek zorunda.

Eğer sharding yaparak verileri 2 GB olacak şekilde farklı sunuculara dağıtırsan belirlediğin bir key üzerinde sorgun sadece 2 GB üzerinde scan edecek bu şekilde daha hızlı çalışacaktır. 

## NoSql vs Sql

TBD

## Indexing

Database indexing verilere daha hızlı erişmek için kullanılan bir yöntemdir. Query'lerde ki conditonlardaki kolonlara göre index konularak sorguların daha hızlı cevap dönmesi sağlanır.

Peki tablolara index koyduğumuzda veri tabanı nasıl daha hızlı cevap döner, index yapısı nasıl çalışır?

Where conditionlı sorgular attığımızda tablolarda ki satırlar condition'larda ki kolonlara göre sıralı olmadığı için veritabanı aradğımız conditionlara göre bütün tabloyu scan eder ve cevap döner.

Biz conditionlarımıza göre index koyduğumuzda veritabanının yaptığı şey aslında index konulan kolonlara göre bir veri yapısı oluşturarak sıralı bir şekilde veri yapısını tutmaktadır. Bu veri yapısı genellikle B-Tree ve onun türevleridir. Veritabanına index'lenmiş kolonlar üzerine bir sorgu geldiği zaman veri tabanı table üzerinde değil bu veri yapısı üzerinde scan yaparak dönmesi gereken satırları bulur. Bu veri yapısında her bir satırın adreside tutulduğu için direk O(1) karmaşıklığı ile datayı bulur ve satırdaki diğer kolonların bilgisini de döner.

Index oluşturmanın dezavantajına gelirsek write operasyonu yaparken hem bu veri yapısnı update edeceği hemde tabloya yazacağı için write operasyonuna ekstra bir yük getirir.

Buna ek olarak bir normal tablo'ya ek olarak birde veri yapısını diske yazdığı için ekstra bir disk maliyeti de getirir.

## Replication

Veritabanına gelen okuma yazma istekleri artık veritabanı sunucusunun kaldıramadığı bir noktaya ulaştığında veritabanını replicate ederek scale edebiliriz bu şekilde veritabanına gelen yükü başka sunuculara dağıtabiliriz.

Master-Slave replication stratejisi ile veritabanını birden fazla sunucuya kopyalabilir bir instance'ı master yapıp okuma ve yazma isteklerini master'a yönlendirebiliriz. Diğer instance'ları da slave yapıp okuma isteklerini de slave instance'lara yönlendirebiliriliz.

Bu şekilde okuma isteklerini farklı sunuculara dağıtmış oluruz.

Burada dikkat edilmesi gereken şey master'a bir yazma işlemi geldiğinde diğer slave'lere yazdığından emin olmasını istersek, slave'lere yazdığına dair acknowledge aldıktan sonra transaction'ı tamamlamasını sağlamak. Bu şekilde instance'lar arası inconsistent state'ler oluşmasını engellemiş oluruz. 

Fakat slave database'lerden acknowledgement bekleyeceği için yazma işleminde gecikmelere neden olabilir.

Yada ack beklemeden direk cevap dönebilir bu şekilde yazma işleminde bir gecikme olmamakla beraber eğer slave'lerden birinde bir down yada sorun olma durumunda inconsitent state'ler olma durumu oluşabilir.

## Partitioning

Veritabanında ki tablolar çok fazla büyüdüğü zaman yapılacak iyileştirmelerden bir tanesi de partitioning yapmaktır. Partitioningi iki şekilde yapabiliriz.

Birtanesi tabloları one to one ilişki ile mantıksal olarak ayrı tablolara bölmek. Çok sık ihtiyaç olan kolonları uygulamadaki iş modeli mantığı çerçevesinde bir tabloda tutup diğer kolonları farklı bir tabloya alabiliriz.

Bir diğer yöntem direk bir partition key belirleyip tabloyu eşit parçalara bölmek.Veritabanı bu şekilde tablolardaki	 dataları farklı file'lara yazar bu da scan edilecek datasetini azaltır. Buna örnek olarak üzerinde ay bilgisi olan bir kolon içeren bir tabloyu 12 parçaya bölebiliriiz. Ay conditionı içeren bir sorgu geldiğinde veritabanı bütün ayları içeren bir dataseti üzerinde scan etmek yerine sadece condition'da ki ayı içeren dataseti scan eder. Bu şekilde veritabanının scan ettiği data hacmini azaltmış oluruz.

## Sharding

Nosql database'lerde eğer database sunucunu işlem yükünü kaldıramayacak noktaya geldiğinde ve datasetiniz çok büyüdüğünde yapılabilecek iyileştirmelerden bir tanesi de sharding yöntemidir.

Sharding ile yapılan şey aslında datasetini belirli kurallar çerçevesinde başka sunuculara dağıtmaktır. Mongodb özelinde örneklendirirsek bir document tipini bir sharding key belirleyerek farkı sunuculara dağıtabiliriz.

Diyelim ki books adında bir döküman tipi var ve biz sorgularımızda kitaplara erişmek için yazar isimlerini kullanıyoruz. Yazar isimlerine göre datayı shardlayıp başka sunuculara dağıtabiliriz.

Eğer mongodb ye bu shard key ile ilgili bir query geldiği zaman verinin hangi sunucuda olduğunu bildiği için o sunucuya yönlendirecek ve bütün datasetini scan etmek yerine sadece o sunucuda ki dataseti ni scan edip sonucu döndürecektir.

Buda sorgularda ki latency problemini iyileştirecektir.

## Sql Tuning

TBD

# Cache

Cache yapıları çok hızlı çalışan data storage mekanizmalarıdır. Genellikle disk değil in memory çalıştıkları için hızlılardır. Uygulamanın gecikme sürelerini düşürmek, veri tabanı üzerinde ki yükü azaltmak için kullanılırlar.

Cache yazma tekniği olarak on demand caching yöntemi bir okuma isteği geldiğinde ilk önce cache bakılıp eğer cachete te yoksa db den okuyup cache e yazıp sonra datayı dönmektir.  Bu şekilde bir sonra ki okuma isteği db yerine cache ten okuyacaktır.

Bir diğer cache yöntemi de önceden cache i doldurmaktır. Bir read isteğinin gelebileceğini bildiğimiz durumlarda cache i önceden doldurarak okuma isteğinin cache ten yapılmasını sağlayabiliriz.

Cache deki dataların zaman içinde state'lerinin eski kalmasını engellemek için ttl süresi vererek belirli bir zaman içinde silinmelerini sağlamak gerekir. Yada veritabanında bir değişiklik olduğu zaman yine cache silerek bu datayı invalidate ederek statelerin db ile senkron olması sağlanabilir.

ResultSet Caching de çok uzun süren db sorgularını direk cachete tutarak veritabanındansa cacheten okunmasıdır. 

# Queue & Communication Between Services

## Queue vs HTTP

TBD

## Kafka

Kafka çok büyük yük trafiği altında çalışabilen bir event streaming plaformudur. Distributed bir şekilde çalışabilidiği için çok yoğun trafik altında çalışabilir. Producer'lar eventleri topiclere yazarlar. Consumer'larda topiclerden verileri okurlar. Topicler partitionlanarak farklı sunuculara(broker) dağıtılabilirler. Bu şekilde distributed şekilde çalışmış olurlar. 

Partitionlar replicate edilebilirler. Bu şekilde her hangi bir partition'ın down olması durumunda diğer replika devreye girerek çalışmaya devam eder.

Streaming özelliği vardır. Topiclerde ki eventleri filtereleme yada dönüştürme işlemşeri yapabilirsin.

Gelen recordları diske yazdığı için record'lar herhangi bir durumda kaybolmazlar. Bu özelliği ile data analytics gibi işlerin kullanımı içinde uygun.

## Kafka vs Queue

TBD

# Microservice & Microservice Design Patterns

* API Gateway
* CQRS(Commang Query Resonsibility Segredation)
* SAGA
* Circuit Breaker
* Service Registers & Service Discovery

# Kubernetes

* Kubernetes benefits

# Spring Boot

* Spring Data Jpa - Hibernate - JPA
* Aspect oriented programming
* Spring IoC structure
* Spring beans lifesycles
* Dependency Injection
* Springte kullanılan design pattern'lar

# Java

## Java 17 Features

### Sealed Classes

Sealed class'lar aslında bir base class'ın hangi class'lar tarafından extend edilebileceğini kısıtlamaya yarıyorlar.

```java
sealed class Vehicle permits Car, Truck { }

final class Car extends Vehicle { }

final class Truck extends Vehicle { }

final class Bicycle extends Vehicle { }
```

Yukarıda ki örnekte Vehicle bir sealed class ve sadece Car ve Truck tarafından extend edilebileceği belirtilmiş. En alttaki Bicycle classını Vehicle'dan extend etmek istediğimiz zaman kod hata verecektir. 

### Records

Records aslında javada data içeren pojo class'larındaki kod kalabalıklarını azaltmaya yarıyor. Getter, setter, constructor, equals, hashCode gibi metodlar ve field definitionlar otomatik olarak eklenmiş oluyorlar.

```java
@Test
void givenAccounts_whenAccountsAreDifferent_shouldNotEquals() {
  // prepare
  record Account(String accountId, String accountOwner) {}
  final Account kaanAccount = new Account("1", "Kaan");
  final Account emreAccount = new Account("2", "Emre");
  // verify
  assertThat(kaanAccount).isNotEqualTo(emreAccount);
  assertThat(kaanAccount.equals(emreAccount)).isFalse();
  assertThat(Objects.equals(kaanAccount, emreAccount)).isFalse();
}
```

```java
@Test
void givenAccounts_whenAccountsAreSame_shouldEquals() {
  // prepare
  record Account(String accountId, String accountOwner) {}
  final Account kaanAccount = new Account("1", "Kaan");
  final Account kaanBackupAccount = new Account("1", "Kaan");
  // verify
  assertThat(kaanAccount).isEqualTo(kaanBackupAccount);
  assertThat(kaanAccount.equals(kaanBackupAccount)).isTrue();
  assertThat(Objects.equals(kaanAccount, kaanBackupAccount)).isTrue();
}
```

### More Declarative NullPointerException

Önceden npe mesajlarında hangi satırda npe aldığı yazardı ama tam olarak hangi parametrenin null olduğunu yazmazken artık hangi parametrenin null olduğu exception mesajında belirtiliyor.

```java
@Test
void givenParameter_whenParameterIsNull_shouldGiveDeclarativeMessage() {
  // prepare
  final Integer num = null;
  final Exception error = catchException(() -> num.doubleValue());
  // verify
  assertThat(error).isInstanceOf(NullPointerException.class);
  assertThat(error.getMessage()).contains("\"num\" is null");
}
```

### No Need to Cast with InstanceOf

InstanceOf ile parametrenin tipini kontrol edip sonra işlem yaparken artık cast etmeye gerek kalmadı.

```java
// Before
if (obj instanceof String) {
    String s = (String) obj;
    // use s
}
// After
if (obj instanceof String s) {
    System.out.println(s.contains("hello"));
}
```

## Known Design Patterns

### Proxy

Bir class'ın bir methoduna metod çalışmadan önce yada çalıştıktan sonra ek operasyonlar yapmasını sağlamaya yarar.

```java
interface HttpHandler {
  
  String handleRequest();
}
```

```java
class HttpHandlerImpl implements HttpHandler {
  
  @Override
  public String handleRequest() {
    return "Success";
  }
}
```

```java
class HttpHandlerPoxy implements HttpHandler {

  private final HttpHandler httpHandler;

  public HttpHandlerPoxy(HttpHandler proxy) {
    this.httpHandler = proxy;
  }

  @Override
  public String handleRequest() {
    return httpHandler.handleRequest() + " 200";
  }
}
```

```java
@Test
void runTest() {
  // Prepare
  var handler = new HttpHandlerImpl();
  var proxy = new HttpHandlerPoxy(handler);
  // Execute
  final String response = handler.handleRequest();
  final String responseWithCode = proxy.handleRequest();
  // Verify
  assertThat(response).isEqualTo("Success");
  assertThat(responseWithCode).isEqualTo("Success 200");
}
```

### Factory

```java
interface Assigner {

  String assign();
}
```

```java
class IndividualAssigner implements Assigner {

  @Override
  public String assign() {
    return "Assign to individual";
  }
}
```

```java
class TeamAssigner implements Assigner {

  @Override
  public String assign() {
    return "Assign to team";
  }
}
```

```java
class WorkGroupAssigner implements Assigner {

  @Override
  public String assign() {
    return "Assign to work group";
  }
}
```

```java
enum AssignmentType {
  INDIVIDUAL,
  TEAM,
  WORK_GROUP
}
```

```java
class AssignerFactory {
  Assigner getAssigner(AssignmentType assignmentType) {
    return switch (assignmentType) {
      case INDIVIDUAL -> new IndividualAssigner();
      case TEAM -> new TeamAssigner();
      case WORK_GROUP -> new WorkGroupAssigner();
    };
  }
}
```

```java
@Test
void runTest() {
  // Prepare
  AssignerFactory assignerFactory = new AssignerFactory();
  var indAssigner = assignerFactory.getAssigner(AssignmentType.INDIVIDUAL);
  var teamAssigner = assignerFactory.getAssigner(AssignmentType.TEAM);
  var workGroupAssigner = assignerFactory.getAssigner(AssignmentType.WORK_GROUP);
  // Verify
  assertThat(indAssigner.assign()).isEqualTo("Assign to individual");
  assertThat(teamAssigner.assign()).isEqualTo("Assign to team");
  assertThat(workGroupAssigner.assign()).isEqualTo("Assign to work group");
}
```

### Strategy

```java
interface TradingStrategy {

  String execute();
}
```

```java
class BullishStrategy implements TradingStrategy {

  @Override
  public String execute() {
    return "Bullish strategy executing";
  }
}
```

```java
class BearishStrategy implements TradingStrategy {

  @Override
  public String execute() {
    return "Bearish strategy executing";
  }
}
```

```java
@AllArgsConstructor
class TradeExecutor {

  private TradingStrategy tradingStrategy;

  void changeStrategy(TradingStrategy tradingStrategy) {
    this.tradingStrategy = tradingStrategy;
  }

  public String trade() {
    return tradingStrategy.execute();
  }
}	
```

```java
@Test
void runTest() {
  // Prepare
  final TradeExecutor tradeExecutor = new TradeExecutor(new BullishStrategy());
  var bullishTrading = tradeExecutor.trade();
  tradeExecutor.changeStrategy(new BearishStrategy());
  var bearishTrading = tradeExecutor.trade();
  // Verify
  assertThat(bullishTrading).isEqualTo("Bullish strategy executing");
  assertThat(bearishTrading).isEqualTo("Bearish strategy executing");
}
```

Decorator

Singleton

## Relation between JVM - JDK - JRE

JDK java programlama dilinde geliştirme yapabilmek için gereken her şeyi sunar.

JRE java uygulamaların derleyip çalıştırmaya yarar.

JVM bytecode'ları yorumlayıp üzerinde çalıştığı platforma göre makine kodlarına çevirip uygulamanın çalışmasını sağlar.

# Unit & Integration Testing