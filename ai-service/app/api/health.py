from fastapi import APIRouter

router = APIRouter(tags=["Health"])


@router.get("/health")
async def health_check() -> dict[str, str]:

    return {
        "status": "UP",
        "service": "banking-genai-ai",
        "version": "1.0.0"
    }