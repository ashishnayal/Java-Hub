"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import Link from "next/link";
import { api, type Topic } from "@/lib/api";
import CodeBlock from "@/components/CodeBlock";

export default function TopicDetailPage() {
  const params = useParams();
  const [topic, setTopic] = useState<Topic | null>(null);
  const [loading, setLoading] = useState(true);
  const [openQuestions, setOpenQuestions] = useState<Set<number>>(new Set());

  useEffect(() => {
    if (params.slug) {
      api.getTopic(params.slug as string).then((data) => {
        setTopic(data);
        setLoading(false);
      }).catch(() => setLoading(false));
    }
  }, [params.slug]);

  function toggleQuestion(index: number) {
    setOpenQuestions((prev) => {
      const next = new Set(prev);
      if (next.has(index)) {
        next.delete(index);
      } else {
        next.add(index);
      }
      return next;
    });
  }

  if (loading) {
    return (
      <div className="loading-container" style={{ minHeight: "60vh" }}>
        <div className="loading-spinner" />
      </div>
    );
  }

  if (!topic) {
    return (
      <div className="empty-state" style={{ minHeight: "60vh" }}>
        <h3>Topic not found</h3>
        <Link href="/topics" className="btn btn-secondary" style={{ marginTop: "1rem" }}>
          ← Back to Topics
        </Link>
      </div>
    );
  }

  return (
    <section className="detail-page" id="topic-detail">
      <div className="container">
        <div className="detail-header animate-fade-in-up">
          <div className="detail-breadcrumb">
            <Link href="/">Home</Link>
            <span className="separator">/</span>
            <Link href="/topics">Topics</Link>
            <span className="separator">/</span>
            <span>{topic.title}</span>
          </div>

          <h1 className="detail-title">{topic.title}</h1>

          <div className="detail-meta">
            <span className={`difficulty-badge difficulty-${topic.difficulty.toLowerCase()}`}>
              {topic.difficulty}
            </span>
            <span style={{ fontSize: "0.85rem", color: "var(--text-muted)" }}>
              {topic.category}
            </span>
            {topic.codeExamples?.length > 0 && (
              <span style={{ fontSize: "0.85rem", color: "var(--text-muted)" }}>
                💻 {topic.codeExamples.length} code examples
              </span>
            )}
            {topic.interviewQuestions?.length > 0 && (
              <span style={{ fontSize: "0.85rem", color: "var(--text-muted)" }}>
                ❓ {topic.interviewQuestions.length} interview questions
              </span>
            )}
          </div>

          <p className="detail-description">{topic.description}</p>
        </div>

        <div className="detail-content">
          <div className="detail-main">
            {/* Content */}
            {topic.content && (
              <div className="markdown-content animate-fade-in-up">
                {topic.content.split("\n").map((line, i) => {
                  if (line.startsWith("## ")) return <h2 key={i}>{line.replace("## ", "")}</h2>;
                  if (line.startsWith("### ")) return <h3 key={i}>{line.replace("### ", "")}</h3>;
                  if (line.startsWith("- **")) {
                    const parts = line.replace("- **", "").split("**");
                    return (
                      <li key={i} style={{ listStyle: "disc", marginLeft: "1.5rem" }}>
                        <strong>{parts[0]}</strong>{parts.slice(1).join("")}
                      </li>
                    );
                  }
                  if (line.startsWith("- ")) return (
                    <li key={i} style={{ listStyle: "disc", marginLeft: "1.5rem" }}>
                      {line.replace("- ", "")}
                    </li>
                  );
                  if (line.startsWith("1. ") || line.startsWith("2. ") || line.startsWith("3. ") || line.startsWith("4. ")) {
                    return (
                      <li key={i} style={{ listStyle: "decimal", marginLeft: "1.5rem" }}>
                        <strong>{line.split("**")[1]}</strong>{line.split("**")[2] || ""}
                      </li>
                    );
                  }
                  if (line.trim() === "") return <br key={i} />;
                  return <p key={i}>{line}</p>;
                })}
              </div>
            )}

            {/* Code Examples */}
            {topic.codeExamples && topic.codeExamples.length > 0 && (
              <>
                <h2 style={{
                  fontFamily: "var(--font-heading)",
                  fontSize: "1.5rem",
                  marginTop: "3rem",
                  marginBottom: "1.5rem",
                }}>
                  💻 Code Examples
                </h2>
                {topic.codeExamples.map((example, i) => (
                  <div key={i} className="animate-fade-in-up" style={{ animationDelay: `${i * 0.1}s` }}>
                    <CodeBlock example={example} />
                  </div>
                ))}
              </>
            )}

            {/* Interview Questions */}
            {topic.interviewQuestions && topic.interviewQuestions.length > 0 && (
              <>
                <h2 style={{
                  fontFamily: "var(--font-heading)",
                  fontSize: "1.5rem",
                  marginTop: "3rem",
                  marginBottom: "1.5rem",
                }}>
                  🎯 Interview Questions
                </h2>
                {topic.interviewQuestions.map((q, i) => (
                  <div key={i} className="interview-card animate-fade-in-up" style={{ animationDelay: `${i * 0.05}s` }}>
                    <div className="interview-question" onClick={() => toggleQuestion(i)}>
                      <span className="interview-q-text">
                        <span className="interview-q-number">Q{i + 1}</span>
                        {q}
                      </span>
                      <span className={`interview-toggle ${openQuestions.has(i) ? "open" : ""}`}>
                        ▼
                      </span>
                    </div>
                    {openQuestions.has(i) && (
                      <div className="interview-answer">
                        This is a common interview question about {topic.title.toLowerCase()}.
                        Refer to the code examples and explanations above for a comprehensive answer.
                        Understanding the underlying concepts and being able to explain them with
                        practical examples will make your answer stand out.
                      </div>
                    )}
                  </div>
                ))}
              </>
            )}
          </div>

          {/* Sidebar */}
          <div className="detail-sidebar">
            <div className="sidebar-card">
              <h3>📋 Topic Info</h3>
              <div className="sidebar-list">
                <div className="sidebar-list-item">Category: {topic.category}</div>
                <div className="sidebar-list-item">Difficulty: {topic.difficulty}</div>
                <div className="sidebar-list-item">Code Examples: {topic.codeExamples?.length || 0}</div>
                <div className="sidebar-list-item">Interview Q&apos;s: {topic.interviewQuestions?.length || 0}</div>
              </div>
            </div>

            {topic.tags && topic.tags.length > 0 && (
              <div className="sidebar-card">
                <h3>🏷️ Tags</h3>
                <div style={{ display: "flex", flexWrap: "wrap", gap: "6px" }}>
                  {topic.tags.map((tag, i) => (
                    <span key={i} className="feature-tag">{tag}</span>
                  ))}
                </div>
              </div>
            )}

            <div className="sidebar-card">
              <h3>🔗 Related</h3>
              <div className="sidebar-list">
                <Link href="/topics" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  All Topics
                </Link>
                <Link href="/interview" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  Interview Corner
                </Link>
                <Link href="/versions" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  Java Versions
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
