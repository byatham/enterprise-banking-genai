from fastapi import APIRouter, HTTPException

from app.schemas.chat import ChatRequest, ChatResponse
from app.services.bedrock_service import BedrockService
from app.prompts.banking_prompts import BANKING_SYSTEM_PROMPT


router = APIRouter(prefix="/chat", tags=["GenAI Chat"])

bedrock_service = BedrockService()


@router.post("/generate", response_model=ChatResponse)
def generate_response(request: ChatRequest):

    try:

        answer = bedrock_service.generate_response(
            question=request.question,
            system_prompt=BANKING_SYSTEM_PROMPT,
        )

        return ChatResponse(
            answer=answer,
            model=bedrock_service.model_id,
            conversation_id=request.conversation_id,
        )

    except Exception as exc:

        # Log the exception internally with a correlation ID.
        # Never return AWS credentials, request details or
        # internal exception messages to the API consumer.

        raise HTTPException(
            status_code=502,
            detail="Unable to generate response using AI service",
        ) from exc