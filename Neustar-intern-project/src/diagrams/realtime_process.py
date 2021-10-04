from urllib.request import urlretrieve

from diagrams import Cluster, Diagram, Edge
from diagrams.gcp.database import SQL
from diagrams.gcp.storage import GCS
from diagrams.custom import Custom
from diagrams.k8s.compute import Pod
from diagrams.gcp.analytics import Dataflow

# Download an image to be used into a Custom Node class
rabbitmq_url = "https://jpadilla.github.io/rabbitmqapp/assets/img/icon.png"
rabbitmq_icon = "rabbitmq.png"
urlretrieve(rabbitmq_url, rabbitmq_icon)

with Diagram("Realtime Process", show=False):
    queue = Custom("Message queue", rabbitmq_icon)
    with Cluster("Processing"):
        handlers = queue >> Pod("Parsing MS") >> Pod("Filter MS") >> Pod("Compliance Action MS")
    
    handlers >> SQL("Database")
    handlers >> GCS("Assets")
    handlers >> queue
    Dataflow("Ingestion") >> queue
