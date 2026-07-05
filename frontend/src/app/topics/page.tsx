"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api, type Topic } from "@/lib/api";

export default function TopicsPage() {
  const [topics, setTopics] = useState<Topic[]>([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState("all");

  useEffect(() => {
    api.getTopics().then((data) => {
      setTopics(data);
      setLoading(false);
    }).catch(() => setLoading(false));
  }, []);

  const categories = ["all", ...Array.from(new Set(topics.map((t) => t.category)))];

  const filtered = filter === "all" ? topics : topics.filter((t) => t.category === filter);

  function getCategoryClass(cat: string) {
    if (cat === "Core Java") return "category-core";
    if (cat === "OOP") return "category-oop";
    if (cat === "Advanced") return "category-advanced";
    if (cat.includes("8")) return "category-java8";
    return "category-java14";
  }

  return (
    <section className="section" id="topics-page">
      <div className="container">
        <div className="section-header">
          <div className="section-label">📚 Knowledge Base</div>
          <h1 className="section-title">Java Topics</h1>
          <p className="section-description">
            Comprehensive guides on every core Java concept — from OOP fundamentals to advanced concurrency and modern language features.
          </p>
        </div>

        <div className="filter-bar" style={{ justifyContent: "center" }}>
          {categories.map((cat) => (
            <button
              key={cat}
              className={`filter-btn ${filter === cat ? "active" : ""}`}
              onClick={() => setFilter(cat)}
            >
              {cat === "all" ? `All (${topics.length})` : cat}
            </button>
          ))}
        </div>

        {loading ? (
          <div className="loading-container">
            <div className="loading-spinner" />
          </div>
        ) : (
          <div className="cards-grid cards-grid-3 stagger-children">
            {filtered.map((t) => (
              <Link
                href={`/topics/${t.slug}`}
                key={t.id}
                className="card topic-card animate-fade-in-up"
              >
                <div className={`topic-card-category ${getCategoryClass(t.category)}`}>
                  {t.category}
                </div>
                <h3 className="topic-title">{t.title}</h3>
                <p className="topic-description">{t.description}</p>
                <div className="topic-footer">
                  <span className={`difficulty-badge difficulty-${t.difficulty.toLowerCase()}`}>
                    {t.difficulty}
                  </span>
                  <span className="topic-qa-count">
                    {t.interviewQuestions?.length || 0} Q&A • {t.codeExamples?.length || 0} Examples
                  </span>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
