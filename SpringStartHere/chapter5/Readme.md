# 📘 Resumo do Capítulo 5
O capítulo 5 explica os dois diferentes escopos de um Bean no Spring: Singleton e Prototype.

## 🟦 Singleton
Esse é o escopo padrão do Spring.
Se você não especificar nada, o Bean será Singleton.

### 🔍 Como funciona
O Spring cria uma única instância daquele Bean e registra com um nome no contexto.
Sempre que esse Bean for solicitado (por injeção ou pelo nome), a mesma instância será retornada.

### 💤 Sobre o @Lazy
Normalmente o Spring cria todos os Beans Singleton na inicialização do contexto.
Com ``@Lazy``, você diz para o Spring não criar o Bean imediatamente, mas apenas quando ele for usado pela primeira vez.

Isso é útil para:
- Beans pesados
- Beans que talvez nunca sejam usados
- Evitar dependências circulares

#### ⚠️ Importante!
Não é recomendado usar atributos mutáveis em Beans Singleton.

**Por quê?**

Porque todos os objetos que recebem esse Bean vão compartilhar o mesmo estado interno.
Se uma parte da aplicação modificar esse estado, isso afeta tudo que depende desse Singleton — o que gera bugs difíceis de rastrear.

Exemplo de atributo mutável problemático

```java
@Component
public class ContadorSingleton {
    private int contador = 0; // atributo mutável

    public void incrementar() {
        contador++;
    }

    public int getContador() {
        return contador;
    }
}
```
Se dois serviços diferentes usarem esse Bean, ambos vão alterar o mesmo contador.

### Por que e quando usamos Singleton

- Quando o Bean não possui estado mutável
- Para serviços que realizam ações stateless, como:
    - serviços de negócio (@Service)
    - proxies/clients HTTP
    - repositórios
- Quando queremos:
    - performance
    - consistência
    - um único ponto de acesso


### ✅ Prós Singleton
- Melhor performance (não cria instâncias repetidamente)
- Menor consumo de memória
- Ideal para objetos sem estado
- Facilita compartilhamento de recursos

### ❌ Contras Singleton
- Não combina com estado mutável
- Pode gerar efeitos colaterais se usado incorretamente
- Difícil de testar quando existe estado interno
- Não adequado para objetos que precisam ser diferentes por operação

## 🟩 Prototype
No escopo Prototype, o Spring cria uma nova instância toda vez que o Bean é solicitado.
Ou seja, o nome do Bean está ligado a um tipo, não a uma instância.

### Como funciona tecnicamente
- Spring cria um novo objeto toda vez que você injeta ou requisita o Bean.
- Depois de criado, o Spring não gerencia mais o ciclo de vida desse objeto.

#### ❗ Observação
Diferente do Singleton, aqui pode ter atributos mutáveis, porque cada objeto é independente.

### 🎯 Por que e quando usamos Prototype
- Quando cada uso precisa de um objeto novo e isolado
- Para Beans com estado
- Objetos que mudam ao longo de uma operação específica
- Em casos como:
    - objetos temporários
    - carrinho de compras
    - sessão de usuário
    - instâncias com informações próprias

### ✅ Prós Prototype
- Cada objeto é independente
- Atributos mutáveis são seguros
- Evita compartilhamento acidental de estado
### ❌ Contras Prototype
- Pode consumir mais memória
- Pode ter custo de criação mais alto
- O Spring não gerencia o final do ciclo de vida (dispensar objetos é responsabilidade sua)
- Não funciona bem se você tentar usar Prototype dentro de Singletons sem configurações extras (porque o Singleton não recebe novas instâncias automaticamente)

## 🧭 Conclusão
O capítulo mostra que:

- Singleton → ideal para serviços sem estado, é o padrão do Spring, mais eficiente.
- Prototype → ideal quando você precisa de uma nova instância a cada uso, especialmente para objetos com estado mutável.

Saber quando usar cada escopo evita bugs por estado compartilhado e melhora a clareza da arquitetura da aplicação.

## 🗺️ Navegação
1. [Singleton utilizando StereotypeAnnotation](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter5/SingletonBeanWithStereotypeAnnotations)

2. [Singleton utilizando ``@Beans``](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter5/SingletonBeanWithBeans)

3. [Singleton ``@Lazy``](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter5/SingletonBeanLazyInstantiation)

4. [Prototype utilizando StereotypeAnnotation](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter5/PrototypeScopedBeanWithStereotypeAnnotations)

5. [Prototype utilizando ``@Beans``](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter5/PrototypeScopedBeanWithBeans)

6. [Exemplo de Prototype no mundo real](https://github.com/LauraBarauna/Books/tree/main/SpringStartHere/chapter5/PrototypeScopedBeanRealWorldScenario)