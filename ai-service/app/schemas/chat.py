from pydantic import BaseModel, Field


class ChatRequest(BaseModel):

    question: str = Field(
        min_length=3,
        max_length=5000,
    )

    conversation_id: str | None = None


class ChatResponse(BaseModel):

    answer: str

    model: str

    conversation_id: str | None = None