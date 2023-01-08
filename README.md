<h1 align="center"> Teste Técnico Attornatus - Gerenciamento de Pessoas</h1>

# Descrição do Projeto
O projeto desenvolvido a seguir tem como objetivo cumprir os requisitos para o teste técnico de Java Junior para a empresa Attornatus, que consiste em criar, listar e atualizar uma pessoa e seu(s) endereço(s).

<a name="indice"></a>
## Índice

* [Índice](#indice)
* [Requisitos Propostos](#requisitos-propostos)
* [Tecnologias Utilizadas](#tecnologias)
* [Conclusão](#conclusao)

<a name="requisitos-propostos"></a>
## Requisitos Propostos
-[x] Criar uma pessoa
> Utilizando uma requisição POST para o cadastro;
`localhost:8080/people`
-[x] Editar uma pessoa
> Utilizando uma requisição PUT para a atualização de pessoa existente;
`localhost:8080/people`
-[x] Consultar uma pessoa
> Utilizando uma requisição GET com o ID para visualizar uma pessoa;
`localhost:8080/people/{ID}`
-[x] Listar pessoas
> Utilizando uma requisição GET pode-se visualizar todas as pessoas cadastradas no sistema;
`localhost:8080/people`
-[x] Criar endereço para pessoa
> Utilizando uma requisição POST para o cadastro do Endereço e informar também o ID da Pessoa de quem é o endereço;
`localhost:8080/address`
-[x] Listar endereços da pessoa
> Utilizando uma requisição GET com o ID da Pessoa é possível observar todos os endereços dela;
`localhost:8080/people/{ID}`
-[x] Poder informar qual endereço é o principal da pessoa
> Utilizando uma requisição PUT para a atualização de um endereço passando o ID do endereço já cadastrado ele ficará como principal, e todos os outros serão alterados automaticamente para não principais;
`localhost:8080/address/{ID}I`

Todos os requisitos propostos foram satisfeitos..