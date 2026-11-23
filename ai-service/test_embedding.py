from app.services.embedding_service import EmbeddingService


def main() -> None:
    service = EmbeddingService()

    text = """
    A customer can request a personal loan.
    Loan approval depends on eligibility and credit assessment.
    """

    embedding = service.generate_embedding(text)

    print(f"Dimensions: {len(embedding)}")
    print(f"First 5 values: {embedding[:5]}")


if __name__ == "__main__":
    main()