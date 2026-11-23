
import os
from functools import lru_cache

from dotenv import load_dotenv
from pydantic_settings import BaseSettings, SettingsConfigDict

# Load .env into process environment
load_dotenv()


class Settings(BaseSettings):

    app_name: str = "banking-genai-ai"

    app_version: str = "1.0.0"

    environment: str = "local"

    database_url: str = (
        "postgresql+psycopg://banking_user:"
        "local_dev_password@localhost:5432/banking_genai"
    )

    redis_url: str = "redis://localhost:6379/0"

    aws_region: str = "ap-south-2"

    aws_bedrock_model_id: str = "amazon.nova-micro-v1:0"

    aws_bedrock_embedding_model_id: str = ""

    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        extra="ignore"
    )


@lru_cache
def get_settings() -> Settings:
    return Settings()
    
    
# Global settings instance
settings = get_settings()
