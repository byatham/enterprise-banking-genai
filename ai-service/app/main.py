from fastapi import FastAPI

from app.api.health import router as health_router
from app.core.config import get_settings


settings = get_settings()

app = FastAPI(
    title=settings.app_name,
    version=settings.app_version,
    description="Enterprise Banking GenAI Platform"
)

app.include_router(
    health_router,
    prefix="/api/v1"
)