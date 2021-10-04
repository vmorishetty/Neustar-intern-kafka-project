from diagrams import Cluster, Diagram
from diagrams.onprem.database import PostgreSQL
from diagrams.gcp.analytics import BigQuery, Dataflow, PubSub
from diagrams.gcp.compute import AppEngine, Functions, GPU
from diagrams.gcp.database import BigTable
from diagrams.gcp.devtools import Scheduler
from diagrams.gcp.iot import IotCore
from diagrams.gcp.storage import GCS
from diagrams.gcp.database import SQL


from diagrams.onprem.queue import Kafka
from diagrams.programming.framework import FastAPI, Spring
from diagrams.outscale.compute import Compute

graph_attr = {
    "fontsize": "45",
    "bgcolor": "red"
}

with Diagram("Ingestion 2"):
    kafka = PubSub("Kafka Message Broker")
    cronJob = Scheduler("Cron Job")
    batch = GPU("Batch Processing")
    rest = Functions("Rest")
    db = SQL("Compliance database")
    process = AppEngine("Process Module")
    with Cluster("Ingestion Module"):
        with Cluster("Compliance Storage"):
            dbscv=[db]
        with Cluster("Rest API"):
            [rest] >> kafka
        with Cluster("Batch API"):
            db << cronJob
            db >> batch >> kafka 
    with Cluster("Processing Module"):
        kafka >> [process]

