# face-a-book
Projeto de uma biblioteca para a disciplina de Programação Orientada a Objetos

## Preparando as coisas

Primeiro, precisa instalar o PostgreSQL. Para isso, basta seguir [esse tutorial](https://r00t4bl3.com/post/how-to-install-postgresql-11-on-linux-mint-19-tara).

É bom rodar o script inicial do banco de dados, que cria as tabelas e tals.

Apos isso, instale o [JDBC do postgres](https://jdbc.postgresql.org/download.html) - para isso, basta incluir o arquivo no `Project Structure - Modules - Dependences - + - Jar or Directories`.

Algumas coisas - como o script de criacao das tabelas - ja estao na pasta `etc` daqui tambem.

## Configurar o banco de dados

Deve-se criar um database denominado face_a_book. Após criar o database, precisa-se configurar as tabelas e tudo mais do banco de dados.
O arquivo etc/reset.sql apresenta os códigos necessários para se configurar o banco de dados (além de poder ser utilizado para voltar às configurações iniciais). Ele também possui a inserção dos usuários padrão do sistema - admin e user.
Também é necessário ter, no diretório do projeto, um arquivo com nome “DataInfo.txt”, que deve conter as informações de nome de usuário, senha e informações do banco de dados. Caso o banco de dados chame face_a_book, basta seguir o exemplo:
```
user
password
jdbc:postgresql://localhost:5432/face_a_book
```

As imagens padrão também precisam ser incluídas no projeto. Para isso, deve-se ter uma pasta “images” no mesmo diretório do projeto com os logos e ícones padrão do sistema. Nesta pasta também serão salvas as outras imagens do sistema.

## Execução
    Feito isso, basta executar o arquivo da forma que preferir - pelo jar (no terminal, basta executar o comando `java -jar face_a_book.jar`) ou diretamente pelo código fonte. Por ser desenvolvido em Java, o programa é compatível com praticamente todos os sistemas operacionais mais utilizados. Foi testado previamente no Windows 10 e Linux Mint.

## Desenvolvedores

André Luís Mendes Fakhoury - Nº 4482145 
Felipe Guilermmo Santuche Moleiro - Nº 10724010 
Felipe Moreira Neves de Souza - Nº 10734651 
Marcus Vinícius Medeiros Pará - Nº 11031663 

## Mais informações

Mais informações podem ser vistas na documentação do projeto (arquivo `.pdf`).
