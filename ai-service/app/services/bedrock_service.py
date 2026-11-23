
import boto3
import logging

from botocore.config import Config
from app.core.config import get_settings

logger = logging.getLogger(__name__)


class BedrockService:

    def __init__(self):

        settings = get_settings()

        self.model_id = settings.aws_bedrock_model_id

        self.client = boto3.client(
            service_name="bedrock-runtime",
            region_name=settings.aws_region,
            config=Config(
                connect_timeout=5,
                read_timeout=60,
                retries={
                    "max_attempts": 3,
                    "mode": "standard",
                },
            ),
        )

    def generate_response(
        self,
        question: str,
        system_prompt: str,
    ) -> str:

        try:

            response = self.client.converse(
                modelId=self.model_id,

                system=[
                    {
                        "text": system_prompt
                    }
                ],

                messages=[
                    {
                        "role": "user",
                        "content": [
                            {
                                "text": question
                            }
                        ],
                    }
                ],

                inferenceConfig={
                    "maxTokens": 1024,
                    "temperature": 0.2,
                    "topP": 0.9,
                },
            )

            return (
                response["output"]["message"]["content"][0]["text"]
            )

        except Exception:
            logger.exception(
                "Error while invoking AWS Bedrock"
            )
            raise
