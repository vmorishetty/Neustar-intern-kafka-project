from diagrams import Diagram, Cluster
from diagrams.gcp.compute import ComputeEngine
from diagrams.gcp.database import SQL
from diagrams.gcp.network import LoadBalancing
from diagrams.onprem.queue import Kafka
from diagrams.gcp.analytics import Dataproc

with Diagram("flow", show=False):
    bus = Kafka("Kafka Message Bus")
    res = LoadBalancing("Result")
    net  = ComputeEngine("Source") >> SQL("Ingestor") >> LoadBalancing("Preprocess") >> bus >> SQL("Action") >> res
    bus << res
    

with Diagram("CCPA Replacement", show=False):
    bus2 = Kafka("Kafka Message Bus")
    with Cluster("Static"):
        compliance = SQL("Compliance")
        access = SQL("Access")
        static_group = [compliance, access]
        access >> bus2

    with Cluster("Dynamic"):
        first = SQL("??")
        second = SQL("??")
        third = SQL("??")
        dynamic_group = [first, second, third]        

    net2 = [SQL("Realtime"), SQL("Batch")] >> bus2 >> Dataproc("Processing") >> SQL("Batch") >> bus2
    bus2 >> static_group
    compliance >> first
    access >> second
    first >> SQL("DSDK")
    second >> SQL("MTA")
    third >> SQL("Gravy")
    