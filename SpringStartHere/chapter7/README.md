# 📘 Resumo do Capítulo 7
O capítulo 7 introduz Spring Boot e Spring MVC.
Ele também revisa conceitos básicos de aplicações web, explicando a diferença entre front-end e back-end.

O primeiro projeto com Spring Boot não é uma API REST, e sim uma aplicação MVC tradicional, onde o back-end também é responsável por renderizar o HTML (server-side rendering).

## 🌐 O que é um Web App
Um web app é uma aplicação que se comunica usando o protocolo HTTP.

- Front-end: parte visual (HTML, CSS, JS)
- Back-end: parte que processa regras de negócio, recebe requisições e envia respostas

Neste capítulo, o Spring é usado para criar um back-end que devolve páginas HTML prontas, e não apenas JSON.

## 🚀 Spring Boot
O Spring, por si só, permite criar aplicações web, mas exige muita configuração.
O Spring Boot surge para resolver isso, aplicando o princípio de:

**Convention over Configuration**

Ou seja:
em vez de você configurar tudo manualmente, o Spring Boot já traz configurações padrão inteligentes, permitindo que você foque no código da aplicação, não na infraestrutura.

### 🔧 O que o Spring Boot faz
O Spring Boot:

- Configura automaticamente um Servlet Container (Tomcat)
- Configura o Spring MVC
- Configura componentes que:
    - interceptam requisições HTTP
    - direcionam requisições para controllers
    - resolvem e renderizam views (HTML)
- Elimina a necessidade de XML ou configurações extensas
- Com isso, você só precisa:
    - criar controllers
    - criar páginas HTML
    - escrever a regra de negócio

### 🐱 Tomcat
Para que uma aplicação Java consiga lidar com requisições HTTP, ela precisa de um Servlet Container.

O Tomcat é um software que:

- recebe requisições HTTP
- traduz essas requisições para o mundo Java
- devolve respostas HTTP ao cliente

Sem um servlet container, você teria que implementar manualmente toda a comunicação via HTTP.

💡 O Spring Boot já vem com o Tomcat embutido, totalmente configurado.

### 🧠 Spring MVC
O Spring MVC é o módulo do Spring responsável por lidar com requisições e respostas HTTP, seguindo o padrão Model–View–Controller.

- Model → dados
- View → HTML
- Controller → recebe a requisição e decide a resposta

### 🏷️ Anotações principais

#### ``@Controller``
Marca uma classe como um Controller do Spring MVC, ou seja, uma classe que recebe requisições HTTP.

```java
@Controller
public class MainController {
}
```

### ``@RequestMapping``
Mapeia um método para uma rota HTTP específica.
```java
@Controller
public class MainController {

    @RequestMapping("/home")
    public String home() {
        return "home.html";
    }

}
```

Sempre que alguém acessar a rota ``/home``, o Spring retorna o conteúdo do arquivo ``home.html``.

**📌 O valor retornado pelo método é o nome da view.**

### 🔄 O que acontece por baixo dos panos (Spring MVC Flow)
1. O cliente faz uma requisição HTTP
2. O Tomcat recebe essa requisição
3. O Tomcat repassa para o DispatcherServlet
4. O DispatcherServlet é o ponto central do Spring MVC
5. Ele chama o Handler Mapping, que procura um método com ``@RequestMapping`` compatível
6. Se não encontrar, retorna 404 Not Found
7. Se encontrar:
    - executa o método do controller
    - recebe o nome da view (HTML)
8. O View Resolver localiza o arquivo HTML correspondente
9. A view é renderizada
10. O HTML final é enviado como resposta HTTP

✨ Tudo isso é 100% automático — você não implementa nada disso manualmente.

## 🧭 Conclusão
Este capítulo apresenta os fundamentos de aplicações web com Spring.

Aprendemos que:
- O Spring Boot simplifica drasticamente a criação de aplicações web
- O Spring MVC organiza o fluxo de requisições e respostas HTTP
- É possível criar aplicações onde o back-end renderiza HTML
- O Tomcat e os componentes do Spring MVC ficam escondidos “por baixo dos panos”

Esse capítulo serve como base para entender:

➡️ controllers

➡️ rotas

➡️ renderização de páginas

➡️ e, futuramente, APIs RES

## 🗺️ Navegação
1. [Primeiro Web App]()