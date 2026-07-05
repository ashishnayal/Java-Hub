"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api, type Topic } from "@/lib/api";

export default function InterviewPage() {
  const [topics, setTopics] = useState<Topic[]>([]);
  const [loading, setLoading] = useState(true);
  const [openQuestions, setOpenQuestions] = useState<Set<string>>(new Set());
  const [selectedTopic, setSelectedTopic] = useState("all");

  useEffect(() => {
    api.getTopics().then((data) => {
      setTopics(data);
      setLoading(false);
    }).catch(() => setLoading(false));
  }, []);

  function toggleQuestion(key: string) {
    setOpenQuestions((prev) => {
      const next = new Set(prev);
      if (next.has(key)) {
        next.delete(key);
      } else {
        next.add(key);
      }
      return next;
    });
  }

  function expandAll() {
    const allKeys = new Set<string>();
    filteredTopics.forEach((t) => {
      t.interviewQuestions?.forEach((_, i) => {
        allKeys.add(`${t.slug}-${i}`);
      });
    });
    setOpenQuestions(allKeys);
  }

  function collapseAll() {
    setOpenQuestions(new Set());
  }

  const filteredTopics = selectedTopic === "all"
    ? topics
    : topics.filter((t) => t.slug === selectedTopic);

  const totalQuestions = topics.reduce(
    (sum, t) => sum + (t.interviewQuestions?.length || 0), 0
  );

  return (
    <section className="section" id="interview-page">
      <div className="container">
        <div className="section-header">
          <div className="section-label">🎯 Interview Prep</div>
          <h1 className="section-title">Interview Corner</h1>
          <p className="section-description">
            {totalQuestions}+ interview questions across {topics.length} topics.
            Quick revision before your next Java interview.
          </p>
        </div>

        {/* Topic Filter */}
        <div className="filter-bar" style={{ justifyContent: "center", marginBottom: "1rem" }}>
          <button
            className={`filter-btn ${selectedTopic === "all" ? "active" : ""}`}
            onClick={() => setSelectedTopic("all")}
          >
            All Topics
          </button>
          {topics.map((t) => (
            <button
              key={t.slug}
              className={`filter-btn ${selectedTopic === t.slug ? "active" : ""}`}
              onClick={() => setSelectedTopic(t.slug)}
            >
              {t.title}
            </button>
          ))}
        </div>

        {/* Controls */}
        <div style={{
          display: "flex",
          justifyContent: "center",
          gap: "12px",
          marginBottom: "2rem",
        }}>
          <button className="btn btn-secondary" onClick={expandAll} style={{ padding: "8px 20px", fontSize: "0.85rem" }}>
            📖 Expand All
          </button>
          <button className="btn btn-secondary" onClick={collapseAll} style={{ padding: "8px 20px", fontSize: "0.85rem" }}>
            📕 Collapse All
          </button>
        </div>

        {loading ? (
          <div className="loading-container">
            <div className="loading-spinner" />
          </div>
        ) : (
          <div style={{ maxWidth: "900px", margin: "0 auto" }}>
            {filteredTopics.map((topic) => (
              <div key={topic.slug} className="animate-fade-in-up" style={{ marginBottom: "2rem" }}>
                {/* Topic Header */}
                <div style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                  marginBottom: "1rem",
                  padding: "1rem 1.5rem",
                  background: "var(--bg-glass)",
                  border: "1px solid var(--border-subtle)",
                  borderRadius: "var(--radius-lg)",
                }}>
                  <div>
                    <Link
                      href={`/topics/${topic.slug}`}
                      style={{ fontFamily: "var(--font-heading)", fontWeight: 700, fontSize: "1.2rem" }}
                    >
                      {topic.title}
                    </Link>
                    <div style={{ fontSize: "0.8rem", color: "var(--text-muted)", marginTop: "4px" }}>
                      {topic.category} • {topic.interviewQuestions?.length || 0} questions
                    </div>
                  </div>
                  <span className={`difficulty-badge difficulty-${topic.difficulty.toLowerCase()}`}>
                    {topic.difficulty}
                  </span>
                </div>

                {/* Questions */}
                {topic.interviewQuestions?.map((q, i) => {
                  const key = `${topic.slug}-${i}`;
                  return (
                    <div key={key} className="interview-card">
                      <div className="interview-question" onClick={() => toggleQuestion(key)}>
                        <span className="interview-q-text">
                          <span className="interview-q-number">Q{i + 1}</span>
                          {q}
                        </span>
                        <span className={`interview-toggle ${openQuestions.has(key) ? "open" : ""}`}>
                          ▼
                        </span>
                      </div>
                      {openQuestions.has(key) && (
                        <div className="interview-answer">
                          <p>
                            This is a key interview question about <strong>{topic.title}</strong>.
                            To answer this well, make sure you understand the core concepts and can
                            provide practical code examples.
                          </p>
                          <p style={{ marginTop: "8px" }}>
                            📚 <Link
                              href={`/topics/${topic.slug}`}
                              style={{ color: "var(--java-orange)", textDecoration: "underline" }}
                            >
                              Read the full {topic.title} guide with code examples →
                            </Link>
                          </p>
                        </div>
                      )}
                    </div>
                  );
                })}
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
