# STOOMSTORE
 Este projeto é uma demonstração prática de como aplicar conceitos de arquitetura e design de software para construir um sistema flexível e orientado a domínio. O foco principal é mostrar a implementação de um mecanismo de descontos em pedidos, feito de forma desacoplada de frameworks específicos e tecnologias como REST, GraphQL, ou CLI, expondo somente os use cases.

## Conceitos e Padrões Utilizados
### Domain-Driven Design (DDD)
Adotamos alguns conceitos de Domain-Driven Design para guiar a modelagem do nosso domínio de negócios. Isso nos permitiu focar nas regras e lógicas de negócio, mantendo o domínio no centro do design do projeto. Com o DDD, asseguramos que a complexidade do negócio esteja bem encapsulada e expressa no código.

## Clean Architecture
Utilizamos a Clean Architecture para garantir que nosso design seja independente de frameworks e tecnologias externas. Isso resulta em um sistema mais testável, manutenível e adaptável a mudanças tecnológicas ou de negócios. A arquitetura em camadas nos permite isolar a lógica de negócios de interfaces de usuário, infraestrutura e detalhes externos.

## Design Patterns
Utilizamos o pattern Factory para implementar os discontos aos pedidos

## SOLID Principles
Alguns princípios SOLID foram seguidos para garantir um design de software coeso, robusto e fácil de manter. Cada princípio ajudou a orientar nossas decisões de design, desde a responsabilidade única de nossas classes até a inversão de dependências para desacoplamento e flexibilidade.

## Implementação Orientada a Domínio
O sistema foi construído com uma forte orientação ao domínio, especialmente na implementação do mecanismo de descontos em pedidos. Isso significa que nosso design reflete o domínio do negócio de forma fidedigna e flexível, adaptando-se facilmente a diferentes regras e políticas de desconto.

## Uso e Aplicabilidade
O projeto é um exemplo excelente de como desacoplar regras de negócio de tecnologias específicas, mantendo a lógica de domínio pura e independente. Isso torna nossa implementação útil em diferentes contextos, seja como parte de uma API REST, um serviço GraphQL, uma interface de linha de comando ou qualquer outra 
