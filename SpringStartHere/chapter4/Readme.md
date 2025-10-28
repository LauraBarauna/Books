# 📘 Resumo do Capítulo 4
O capítulo 4 explica como dissociar implementações utilizando interfaces e como o Spring identifica e injeta automaticamente essas implementações no Spring Context.
Em outras palavras, ele mostra como deixar o código mais flexível, modular e fácil de alterar sem precisar mudar o restante do sistema.

## 🧩 Interfaces em Java
Geralmente, utilizamos interfaces em Java para promover a dissociação entre o código que define um comportamento e as diversas classes que o implementam. Esse princípio é fundamental para a abstração e o baixo acoplamento.

### Exemplo prático
Imagine que você está construindo um aplicativo de envio de pacotes.
Você tem uma classe ``DeliveryDetailsPrinter``, responsável por imprimir os detalhes das entregas, e precisa ordenar os pacotes antes de imprimir.

```java
public class DeliveryDetailsPrinter {

    private SorterByAddress sorter;

    public DeliveryDetailsPrinter(SorterByAddress sorter) {
        this.sorter = sorter;
    }

    public void printDetails() {
        this.sorter.sortDetails();
    }
}

```

```java
public class SorterByAddress {

    public void sortDetails() {
        // código de ordenação
    }
}

```
Agora, se você quiser ordenar por nome do cliente em vez de endereço, precisaria criar uma nova classe
``(SorterByClientName)`` e mudar o código da ``DeliveryDetailsPrinter``, o que quebra o princípio de desacoplamento.

### Solução: usar uma interface
Com uma interface, você define o comportamento esperado, mas não a forma de implementá-lo.

```java
public interface Sorter {
    void sortDetails();
}

```

```

```java
public class SorterByAddress implements Sorter {
    @Override
    public void sortDetails() {
        // ordena por endereço
    }
}

```

```java
public class SorterByClientName implements Sorter {
    @Override
    public void sortDetails() {
        // ordena por nome do cliente
    }
}

```

E na classe principal:

```java
public class DeliveryDetailsPrinter {

    private Sorter sorter;

    public DeliveryDetailsPrinter(Sorter sorter) {
        this.sorter = sorter;
    }

    public void printDetails() {
        this.sorter.sortDetails();
    }
}
```

Agora você pode trocar a implementação simplesmente passando outro ``Sorter`` — sem modificar a classe ``DeliveryDetailsPrinter``.

➡️ Isso é dissociação de implementação por injeção de dependência.

## ☕ Adicionando implementações de interfaces ao Spring Context

### 1. Usando Stereotype Annotations
O Spring consegue gerenciar automaticamente as classes que implementam interfaces.
Basta colocar uma anotação como ``@Component``, ``@Service``, ou ``@Repository`` sobre elas.

Se uma classe tem apenas um construtor, o ``@Autowired`` não é obrigatório — o Spring detecta a dependência automaticamente.

```java
@Component
public class SorterByAddress implements Sorter { ... }

@Component
public class DeliveryDetailsPrinter {
    private final Sorter sorter;

    public DeliveryDetailsPrinter(Sorter sorter) {
        this.sorter = sorter;
    }
}
```

### 2. Usando @Autowired
Você pode injetar a interface de várias formas — por campo, construtor ou setter.
O recomendado continua sendo construtor, pois é mais seguro e testável.

```java
@Component
public class DeliveryDetailsPrinter {

    private final Sorter sorter;

    @Autowired
    public DeliveryDetailsPrinter(Sorter sorter) {
        this.sorter = sorter;
    }
}

```

### 3. Usando @Bean
Outra forma é registrar manualmente os Beans das implementações dentro de uma classe de configuração:

```java
@Configuration
public class ProjectConfig {

    @Bean
    public Sorter sorter() {
        return new SorterByAddress();
    }

    @Bean
    public DeliveryDetailsPrinter deliveryDetailsPrinter(Sorter sorter) {
        return new DeliveryDetailsPrinter(sorter);
    }
}
```
(O processo é o mesmo visto no capítulo 3, apenas aplicando agora a uma interface.)

[Acesse o resumo do capítulo 3 aqui]()

##⚖️ Quando há várias implementações da mesma interface
Se uma interface tiver múltiplas implementações, o Spring não saberá qual injetar.
Existem duas formas principais de resolver isso: ``@Primary`` e ``@Qualifier``.

### 🥇 ``@Primary``
Marque a implementação principal com @Primary.
O Spring usará essa por padrão sempre que encontrar mais de uma.

```java
@Component
@Primary
public class CommentPushNotificationProxy implements CommentNotificationProxy {

    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending push notification for comment " + comment.getText());
    }
}

```

#### ✅ Prós
1. Fácil de aplicar — apenas uma anotação.
2. Boa opção quando existe uma implementação padrão.

#### ❌ Contras
1. Limita o projeto a uma única implementação preferencial.
2. Pode gerar confusão se houver muitas classes e múltiplos @Primary acidentais.

###🎯 ``@Qualifier``
Com ``@Qualifier``, você pode dar um nome único a cada implementação e indicar qual deve ser usada..

```java
public interface CommentNotificationProxy {

    void sendComment(Comment comment);
}
```

```java
@Component
@Qualifier("PUSH")
public class CommentPushNotificationProxy implements CommentNotificationProxy{

    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending push notification for comment "
                + comment.getText());
    }
}
```

```java
@Component
@Qualifier("EMAIL")
public class EmailCommentNotificationProxy implements CommentNotificationProxy{

    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending notification for comment "
        + comment.getText());
    }
}
```

```java
@Component
public class CommentService {


    private CommentRepository commentRepository;
    private CommentNotificationProxy commentNotificationProxy;

    public CommentService(CommentRepository commentRepository,
                         @Qualifier("PUSH") CommentNotificationProxy commentNotificationProxy) {
        this.commentRepository = commentRepository;
        this.commentNotificationProxy = commentNotificationProxy;
    }

    public void publishComment(Comment comment) {
        this.commentRepository.storeComment(comment);
        this.commentNotificationProxy.sendComment(comment);
    }
}
```

#### ✅ Prós
1. Total controle sobre qual implementação é injetada.
2. Permite coexistência de múltiplas versões de um mesmo serviço.

#### ❌ Contras
1. Exige mais configuração manual.
2. Pode gerar confusão se os nomes dos qualifiers não forem padronizados.

## 🧱 Usando as Stereotype Annotations corretas
O capítulo mostra que nem sempre devemos usar apenas ``@Component``.
Existem anotações específicas para indicar a função da classe dentro da aplicação:

| Anotação      | Uso principal                                                                          |
| ------------- | -------------------------------------------------------------------------------------- |
| `@Component`  | Classe genérica, usada quando não se encaixa nas outras categorias.                    |
| `@Service`    | Classes que contêm **regras de negócio** ou **lógica de aplicação**.                   |
| `@Repository` | Classes responsáveis por **acesso a dados** (banco de dados, arquivos, APIs externas). |


💡 Essas anotações ajudam o Spring a entender o papel de cada classe, além de aplicar tratamentos automáticos (como tradução de exceções em ``@Repositor``y).

## 🧭 Conclusão
O Capítulo 4 aprofunda o uso de injeção de dependência com interfaces, mostrando como o Spring permite alternar implementações de forma simples e desacoplada.
Você aprendeu a:

- Usar interfaces para abstrair comportamentos e permitir múltiplas implementações;
- Deixar o Spring gerenciar essas dependências com @Autowired e Stereotype Annotations;
- Controlar qual implementação usar com @Primary e @Qualifier;
- Escolher a anotação certa (@Component, @Service, @Repository) para cada tipo de classe.

Esses conceitos são fundamentais para criar arquiteturas modulares, testáveis e fáceis de manter em aplicações Spring.

## 🗺️ Navegação
1. [``Exemplo de Interface sem Spring``]()
2. [``Stereotype Annotations``]()
3. [``@Autowired``]()
4. [``@Bean``]()
5. [``@Primary``]()
6. [``@Qualifier``]()
7. [``Stereotype Annotations corretas``]()