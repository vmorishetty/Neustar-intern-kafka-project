from diagrams import Diagram, Cluster
from diagrams.gcp.database import SQL
from diagrams.onprem.queue import Kafka
from diagrams.gcp.database import Datastore

with Diagram("Realtime Project", show=False):
        with Cluster("VSCode"):
            
            kafka = Kafka("Kafka")
            ingest = SQL("Ingestion")
            zoo = SQL("Zookeeper")
            consumer  =SQL("Consumer")
            db = Datastore("DataBase")
            minio = Datastore("minio")
            SQL("App") >> ingest >> kafka >> consumer >>db 
            kafka >> minio
            kafka >> zoo
            zoo >> kafka
            ingest >> db