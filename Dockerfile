FROM java:8

CMD mkdir /apps

ADD app.json .
ADD target/text-analysis-playground-swarm.jar .

CMD java -Djboss.socket.binding.port-offset=10000 -jar text-analysis-playground-swarm.jar app.json
