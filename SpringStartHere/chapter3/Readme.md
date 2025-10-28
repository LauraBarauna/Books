# 📘 Resumo do Capítulo 3
No capítulo 3, o livro explica como estabelecer relacionamentos entre Beans.
Por exemplo, uma classe Person pode ter um atributo do tipo Parrot.
Esse tipo de ligação — um objeto dentro de outro — é o que chamamos de relacionamento entre classes, e o Spring pode gerenciar isso automaticamente através de injeção de dependências (Dependency Injection).

## 🤝 Como estabelecer relacionamentos entre Beans
Existem três maneiras principais de criar relacionamentos entre Beans dentro do Spring Context.

### 1. Definindo dentro do método @Bean
A forma mais direta é referenciar o objeto dentro do mesmo método que o adiciona ao contexto.

```java
    @Bean
    public Parrot parrot() {
        Parrot p = new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    public Person person() {
        Person p = new Person();
        p.setName("Ella");
        p.setParrot(parrot());
        return p;
    }
```

Aqui, chamamos p.setParrot(parrot()).
Pode parecer que o método parrot() seria executado de novo e criaria outro objeto, mas o Spring é inteligente: ele percebe que esse Bean já existe no contexto e reutiliza a mesma instância, sem duplicar.

#### Prós
1. Simples de entender e implementar.
2. Não depende de anotações adicionais.
3. Controle total sobre a criação e configuração dos objetos.

#### Contras
1. Torna o código de configuração mais verboso.
2. Requer dependências explícitas entre métodos (um chamando o outro), o que pode dificultar a leitura em projetos grandes.

### 2. Usando a anotação @Autowired
Com @Autowired, o Spring injeta automaticamente o Bean certo na classe onde for necessário.
Essa injeção pode acontecer de três formas diferentes:

1. Field Injection
```java
@Component
public class Person {
    private String name = "Ella";

    @Autowired
    private Parrot parrot;
}
```

Essa forma injeta diretamente no atributo.

#### ⚠️ Por que não é recomendada?

- Ela viola o princípio da imutabilidade (não dá pra tornar o campo final).
- Dificulta os testes, pois você não pode facilmente passar dependências via construtor.
- O Spring precisa usar reflection para injetar, o que reduz clareza e segurança.

2. Constructor Injection
```java
@Component
public class Person {
    private String name = "Ella";
    private final Parrot PARROT;

    @Autowired
    public Person(Parrot parrot) {
        this.PARROT = parrot;
    }
}
```

#### ✅ Por que é a forma mais recomendada?

- Garante que o objeto só pode ser criado com todas as dependências necessárias.
- Permite tornar os campos final, aumentando a imutabilidade.
- Facilita a escrita de testes unitários e o uso de mocks.
- É o estilo usado em Spring moderno (inclusive no Spring Boot).

3. Setter Injection
```java
@Component
public class Person {
    private String name = "Ella";
    private  Parrot parrot;

    @Autowired
    public void setParrot(Parrot parrot) {
        this.parrot = parrot;
    }
}
```
Essa abordagem é útil quando a dependência é opcional ou pode ser alterada após a criação do objeto.
Porém, não permite atributos final, e geralmente é menos usada.

#### ✅ Prós do @Autowired
1. Código mais limpo e menos acoplado.
2. Reduz a necessidade de configurar manualmente os Beans.
3. Funciona de forma automática com as Stereotype Annotations (@Component, @Service, etc.).

#### ❌ Contras do @Autowired
1. Pode gerar ambiguidade quando há múltiplos Beans do mesmo tipo.
2. Field Injection e Setter Injection reduzem clareza e testabilidade.
3. Exige que o Spring saiba onde procurar os Beans (pacote escaneado).

### 3. Usando @Qualifier
Quando existem vários Beans do mesmo tipo, o Spring não sabe qual deve injetar.
Para resolver isso, usamos ``@Qualifier`` para indicar o nome específico do Bean.
```java
@Configuration
@ComponentScan(basePackages = "main")
public class ProjectConfig {

    @Bean
    public Parrot parrot1() {
        Parrot p = new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    public Parrot parrot2() {
        Parrot p = new Parrot();
        p.setName("Miki");
        return p;
    }

}
```
Na classe que depende do Parrot:
```java
@Component
public class Person {
    private String name;
    private Parrot parrot;

    public Person(@Qualifier("parrot2") Parrot parrot) {
        this.parrot = parrot;
    }
}
```
Ou, se você estiver configurando com @Bean:
```java
@Configuration
public class ProjectConfig {

    @Bean
    public Parrot parrot1() {
        Parrot p = new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    public Parrot parrot2() {
        Parrot p = new Parrot();
        p.setName("Miki");
        return p;
    }

    @Bean
    public Person person (@Qualifier("parrot2") Parrot parrot) {
        Person p = new Person();
        p.setName("Ella");
        p.setParrot(parrot);
        return p;
    }

}
```
💡 Assim, o Spring sabe exatamente qual instância injetar.

## 🔄 Circular Dependency
Uma dependência circular acontece quando duas classes dependem uma da outra diretamente.

```java
@Component
public class A {
    @Autowired
    private B b;
}

@Component
public class B {
    @Autowired
    private A a;
}
```
Isso causa um erro no Spring, pois ele não consegue criar nenhum dos dois objetos —
um depende do outro antes de existir.
A solução é repensar o design das classes e eliminar o acoplamento direto, usando interfaces, eventos ou serviços intermediários.

## 🧭 Conclusão
O Capítulo 3 mostra como o Spring injeta dependências automaticamente, gerenciando as relações entre objetos.
Você pode criar relacionamentos manualmente dentro dos métodos ``@Bean``, usar injeção automática com ``@Autowired``, e definir qual Bean usar com ``@Qualifier``.
Essas práticas tornam o código mais limpo, modular e fácil de manter.

## 🗺️ Navegação
1. [``@Bean``](https://github.com/LauraBarauna/Books/blob/main/SpringStartHere/chapter3/ex1/src/main/java/config/ProjectConfig.java)
2. [Múltiplos Beans](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter3/multipleBeans/src)
3. [Múltiplos Beans com ``@Autowired``](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter3/multipleBeansAutoWired/src)
4. [``@Autowired`` no Construtor](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter3/autowiredConstructor/src/main/java)
5. [``@Autowired`` no Setter](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter3/autowiredSetter)
