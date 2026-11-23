from fastapi import APIRouter, HTTPException
from pydantic import BaseModel
import logging

from app.services.bedrock_service import BedrockService

logger = logging.getLogger(__name__)

router = APIRouter(
    prefix="/hello",
    tags=["AI Hello"]
)

bedrock_service = BedrockService()


class HelloResponse(BaseModel):
    message: str
    ai_response: str
    model: str


@router.get("", response_model=HelloResponse)
def hello_ai():

    try:

        answer = bedrock_service.generate_response(
            question="Hello AI! Please introduce yourself in one sentence.",
            system_prompt="You are a friendly AI assistant."
        )

        return HelloResponse(
            message="Hello from Python FastAPI!",
            ai_response=answer,
            model=bedrock_service.model_id
        )

    except Exception:
        logger.exception("Hello AI endpoint failed")

        raise HTTPException(
            status_code=502,
            detail="Unable to connect to AI service"
        )