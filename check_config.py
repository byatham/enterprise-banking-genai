import os
from dotenv import load_dotenv

load_dotenv()

print("AWS Region:", os.getenv("AWS_REGION"))
print("Model ID:", os.getenv("BEDROCK_MODEL_ID"))

key = os.getenv("AWS_BEARER_TOKEN_BEDROCK")
print("API Key configured:", bool(key))