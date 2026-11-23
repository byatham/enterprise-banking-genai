from fastapi import FastAPI

from app.api.health import router as health_router
from app.api.v1.chat import router as chat_router

from app.core.config import get_settings
import logging

from app.api.v1.hello import router as hello_router



settings = get_settings()

app = FastAPI(
    title=settings.app_name,
    version=settings.app_version,
    description="Enterprise Banking GenAI API",
)

app.include_router(
    health_router,
    prefix="/api/v1",
)

app.include_router(
    chat_router,
    prefix="/api/v1",
)



logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s %(levelname)s %(name)s: %(message)s"
)

app.include_router(
    hello_router,
    prefix="/api/v1"
)