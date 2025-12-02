# 📘 Resumo do Capítulo 6
O capítulo 6 explica o conceito de Aspects (AOP – Aspect Oriented Programming).
Eles permitem separar preocupações transversais (como log, segurança, performance e validação) da regra de negócio principal.

Os Aspects funcionam como interceptadores: eles envolvem, observam ou controlam a execução de métodos definidos no Spring Context.

Isso deixa suas classes mais limpas e focadas apenas no que realmente importa: a lógica de negócio.

## 🎭 Analogia
Imagine que seu método é uma pessoa entrando em um prédio:

- 📋 Um segurança confere os dados (Aspect de segurança)
- 🪪 Uma câmera registra a entrada (Aspect de log)
- 🚪 A porta se abre (execução do método real)

## 💻 Exemplo simples
**Classe de regra de negócio**
```java
@Component
public class CommentService {

    public void publishComment(String comment) {
        System.out.println("Publishing comment: " + comment);
    }
}

```
**Aspect de Log**
```java
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* services.CommentService.publishComment(..))")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before method...");

        joinPoint.proceed(); // executa o método real

        System.out.println("After method...");
    }
}
```
**Saída no console**
```
Before method...
Publishing comment: Hello
After method...
```
O método ``publishComment()`` não sabe que está sendo monitorado.
Quem controla isso é o Aspect.

### ⚠️ Observação importante
Aspects tornam o código mais limpo, mas podem se tornar perigosos se:

- Você colocar muita lógica dentro deles
- Tornar o fluxo da aplicação difícil de entender
- Usar para coisas que deveriam estar na regra de negócio

Aspect deve ser usado para:
- ✔ logs
- ✔ validações transversais
- ✔ segurança
- ✔ métricas
- ✔ monitoramento
- ✖ lógica principal do sistema

## 🛠️ Como utilizar Aspects no Spring
Você precisa de três coisas:

1. Uma classe com @Aspect
2. Registrar essa classe no Spring (@Component ou @Bean)
3. Ativar AOP no projeto com:

```java
@EnableAspectJAutoProxy
```

**Exemplo na configuração:**
```java
@Configuration
@ComponentScan(basePackages = "main")
@EnableAspectJAutoProxy
public class ProjectConfig {
}
```

## 🎯 Jeitos de interceptar métodos
O jeito mais poderoso (mas mais complexo) é usando expressões AspectJ, como:
```java
@Around("execution(* services.*.*(..))")
```
Porém, o jeito mais limpo e organizado é:
1. Criando a anotação
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ToLog {
}
```
2. Usando no método
```java
@ToLog
public void publishComment(String comment) {
    System.out.println("Publishing comment...");
}
```
3. Capturando no Aspect
```java
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(ToLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before method...");

        Object result = joinPoint.proceed();

        System.out.println("After method...");
        return result;
    }
}
```
Isso é muito melhor porque:

- Seu código fica mais limpo
- Você controla tudo por anotação
- Mais fácil de manter

## 🔁 Tipos de Aspects
| Anotação          | O que faz                                          |
| ----------------- | -------------------------------------------------- |
| `@Before`         | Executa **antes** do método                        |
| `@After`          | Executa **depois**, independentemente do resultado |
| `@Around`         | Envolve o método (mais completo e poderoso)        |
| `@AfterThrowing`  | Só executa quando ocorre uma exceção               |
| `@AfterReturning` | Só executa se o método terminar com sucesso        |

O mais utilizado é o ``@Around``, pois ele dá controle total sobre a execução.

## 🔗 Múltiplos Aspects no mesmo método
Um mesmo método pode ser interceptado por vários Aspects:

- Log
- Segurança
- Medição de tempo

Isso cria uma cadeia de execução.
Se a ordem não for definida, o Spring decide.

Para controlar isso, usamos:
```java
@Order(1)
```
Exemplo:
```java
@Aspect
@Component
@Order(1)
public class SecurityAspect { ... }

@Aspect
@Component
@Order(2)
public class LoggingAspect { ... }
```
Agora:
- 1º Segurança
- 2º Log

Isso evita comportamentos inesperados.


## 🧭 Conclusão
O capítulo 6 mostra que:

- Aspects servem para separar responsabilidades transversais
- Deixam a lógica de negócio mais limpa e organizada
- Devem ser usados com cuidado para não esconder regras importantes
- São uma ferramenta muito poderosa do Spring
- Quando usados corretamente, tornam sua aplicação mais profissional, modular e fácil de manter.

## 🗺️ Navegação
1. [Como utilizar Aspects](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter6/ApectsFirstExample/src/main/java)

2. [Alterando parametros e retorno dos métodos com Aspects](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter6/ChangeParametersAndReturns/src/main/java)

3. [Aspects com anotação personalizada](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter6/AspectsWithAnnotations/src/main/java)

4. [Múltiplos Aspects no mesmo método](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter6/MultiplesAspects/src/main/java)

5. [Alterando ordem dos múltiplos Aspects](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter6/MultiplesAspectsChangeOrder/src/main/java)
