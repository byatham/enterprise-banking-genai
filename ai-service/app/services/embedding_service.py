
import json

from app.core.config import settings

import boto3
from botocore.config import Config
from botocore.exceptions import BotoCoreError, ClientError

from app.core.config import settings


class EmbeddingService:
    """Generates text embeddings using Amazon Bedrock Titan."""

    def __init__(self) -> None:
        self._model_id = settings.aws_bedrock_embedding_model_id
        self._dimensions = settings.embedding_dimensions

        self._client = boto3.client(
            "bedrock-runtime",
            region_name=settings.aws_region,
            config=Config(
                connect_timeout=5,
                read_timeout=30,
                retries={
                    "max_attempts": 3,
                    "mode": "standard",
                },
            ),
        )

    def generate_embedding(self, text: str) -> list[float]:
        if not text or not text.strip():
            raise ValueError("Text cannot be empty.")

        request_body = {
            "inputText": text.strip(),
            "dimensions": self._dimensions,
            "normalize": True,
        }

        try:
            response = self._client.invoke_model(
                modelId=self._model_id,
                body=json.dumps(request_body),
                contentType="application/json",
                accept="application/json",
            )

            result = json.loads(response["body"].read())
            embedding = result["embedding"]

        except (BotoCoreError, ClientError, KeyError, ValueError) as exc:
            raise RuntimeError(
                "Failed to generate text embedding with Bedrock."
            ) from exc

        if len(embedding) != self._dimensions:
            raise RuntimeError(
                f"Expected {self._dimensions} dimensions, "
                f"received {len(embedding)}."
            )

        return embedding
