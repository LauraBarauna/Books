# 📘 Resumo do Capítulo 2
No capítulo 2, o livro explica como adicionar objetos ao Spring Context — ou seja, como fazer com que o
Spring gerencie as instâncias das classes da sua aplicação.

## 🪣 O que é o Spring Context
Pense no Spring Context como um balde mágico onde ficam todos os objetos que o Spring gerencia para você.
Em vez de você criar e controlar manualmente os objetos com new, o Spring faz isso automaticamente — ele cria, inicializa, armazena e entrega esses objetos quando necessário.

#### 🧠 Analogia:
Imagine que você está em uma cozinha profissional. Você (o programador) não precisa preparar todos os ingredientes — o Spring Context é como uma despensa organizada que já guarda tudo pronto para uso. Quando você precisa de algo, é só pedir que ele entrega o objeto certo.

### 🌱 Benefícios do Spring Context
- Menos acoplamento: o código não precisa se preocupar em criar objetos manualmente.

- Reutilização e consistência: o mesmo objeto pode ser compartilhado em diferentes partes do sistema.

- Facilidade de teste: é mais simples trocar implementações (por exemplo, um mock em vez de um objeto real).

- Gerenciamento centralizado: o Spring controla o ciclo de vida de cada objeto, como criação e destruição.

## 🧩 O que são Beans
Beans são as instâncias dos objetos que ficam dentro do Spring Context.
Sempre que o Spring cria um objeto e o gerencia, dizemos que ele é um bean.

## ⚙️ Como adicionar Beans no Spring
Existem três maneiras principais de adicionar um bean ao Spring Context:

💡 Em todas elas, você precisa criar uma classe de configuração com a anotação @Configuration.
E, no método main, deve instanciar o contexto com:
```java
var context = new AnnotationConfigApplicationContext(NomeDaClasseDeConfiguracao.class);
```

### 1. 🧱 @Bean Annotation
Você pode criar um método dentro da sua classe de configuração que retorna o objeto a ser gerenciado e anotar esse método com @Bean.

```java
@Configuration
public class ProjectConfig {

    @Bean
    public Parrot parrot() {
        Parrot p = new Parrot();
        p.setName("Kiki");
        return p;
    }
}
```

#### ✅ Prós
1. Permite criar qualquer tipo de instância.
2. Dá controle total sobre como o objeto é criado.
3. Suporta múltiplas instâncias do mesmo tipo.

#### ❌ Contras
1. Exige mais código — cada instância precisa de um método próprio.

#### ⚡ Múltiplas instâncias do mesmo objeto
Se você tiver dois métodos @Bean retornando o mesmo tipo, por exemplo:

```java
@Bean
public Parrot parrot1() { ... }

@Bean
public Parrot parrot2() { ... }
```

Ao buscar o bean, você precisa informar o nome do método, pois existem duas opções:

```java
Parrot p = context.getBean("parrot2", Parrot.class);
```

Caso você tente buscar apenas por tipo (``context.getBean(Parrot.class)``), o Spring lançará um erro, pois não saberá qual instância escolher.

### 2. 🧬 Stereotype Annotations
Outra forma é usar anotações como ``@Component``, ``@Service``, ``@Repository`` ou ``@Controller``.
Basta colocar uma dessas anotações acima da classe, e o Spring criará automaticamente um bean para ela.

Na classe de configuração, você precisa apenas indicar em qual pacote o Spring deve procurar essas classes anotadas:

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class ProjectConfig { }
```
#### ✅ Prós
1. Muito menos código — o Spring faz o trabalho pesado.
2. A configuração fica mais limpa e organizada.

#### ❌ Contras
1. Menor controle sobre a criação do objeto (por exemplo, se precisar inicializar de um jeito específico).

### 3. 🧾 ``registerBean()``
Esse método é uma forma programática de registrar beans no contexto, sem usar anotações.

Você pode fazer isso diretamente no main, ou em algum ponto do código, usando o próprio contexto:

```java
var context = new AnnotationConfigApplicationContext();

context.registerBean(Parrot.class, Parrot::new);
context.refresh();

Parrot p = context.getBean(Parrot.class);
```

#### ✅ Prós
1. Não precisa de classes de configuração.
2. Útil para criar beans dinamicamente (por exemplo, a partir de um arquivo externo ou condição).

#### ❌ Contras
1. Menos usado em projetos convencionais.
2. Menos legível que as outras opções.

## 🧭 Conclusão
O Capítulo 2 ensina como o Spring assume o controle da criação dos objetos para você.
Ao usar o Spring Context e os Beans, o código fica mais limpo, desacoplado e fácil de manter.
Você pode adicionar beans manualmente ``@Bean``, automaticamente ``@Component`` ou de forma programática ``registerBean()``, dependendo da necessidade e do controle que quiser ter.

## 🗺️ Navegação
1. [``@Bean``]()
2. [``@Component``]()
3. [``registerBean()``]()