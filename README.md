##Aplicação de upload de arquivos.

Você deve criar uma aplicação web que realize upload de arquivos e liste todos os arquivos enviados utilizando a linguagem de programação Java. O site deve consumir APIs REST para realizar todo o trabalho.

###Pontos importantes:
A aplicação deve ser feita em Java
Toda a API deve estar documentada utilizando o Swagger
O código fonte deve estar hospedado no Github ou ser enviado por e-mail

###API de upload
Os arquivos devem ser enviados em blocos (“chunks”). 
Cada bloco deve ter no máximo 1MB.
Além do conteúdo do arquivo a API deve receber um identificador do usuário.

###API de listagem
A API deve retornar uma listagem de arquivos.
Campos retornados: 
- Identificador do usuário que enviou o arquivo
- Nome do arquivo
- Status do upload (Em andamento, falha ou concluído)
- Tempo de envio
- Quantidade de blocos em que o arquivo foi dividido
- Link para download do arquivo

###Observações:
- Não é necessário utilizar banco de dados, para critério de avaliação do teste, tudo pode ser processado em memória ou em disco local, conforme sua preferência. - Caso seja necessário utilizar banco de dados, utilize MySQL e envie junto um dump de estrutura e as configurações de conexão necessárias.
- Podem ser usadas bibliotecas de Javascript à sua escolha.
- Podem ser usadas bibliotecas Java externas para auxílio da tarefa de criação da API, da documentação e do parse dos chunks dos arquivos.
- Não é necessário se preocupar com estética nas páginas solicitadas.
- Importante realizar corretamente tratamentos de erros.
- Podem ser utilizados frameworks para criar aplicação standalone para expor a API, ou também podem ser utilizados servidores de aplicação externos. Nesse caso você deverá informar a exata versão do servidor utilizado e todas as configurações necessárias para rodar a aplicação.
