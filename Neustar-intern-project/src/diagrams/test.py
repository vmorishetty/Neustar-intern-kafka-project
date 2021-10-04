from diagrams import Diagram
from diagrams.gcp.compute import ComputeEngine
from diagrams.gcp.database import SQL
from diagrams.gcp.network import LoadBalancing

with Diagram("Web Service", show=False):
    LoadBalancing("lb") >> ComputeEngine("web") >> SQL("userdb")