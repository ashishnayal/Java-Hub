"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import Link from "next/link";
import { api, type Framework } from "@/lib/api";
import CodeBlock from "@/components/CodeBlock";

export default function FrameworkDetailPage() {
  const params = useParams();
  const [framework, setFramework] = useState<Framework | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (params.slug) {
      api.getFramework(params.slug as string).then((data) => {
        setFramework(data);
        setLoading(false);
      }).catch(() => setLoading(false));
    }
  }, [params.slug]);

  if (loading) {
    return (
      <div className="loading-container" style={{ minHeight: "60vh" }}>
        <div className="loading-spinner" />
      </div>
    );
  }

  if (!framework) {
    return (
      <div className="empty-state" style={{ minHeight: "60vh" }}>
        <h3>Framework not found</h3>
        <Link href="/frameworks" className="btn btn-secondary" style={{ marginTop: "1rem" }}>
          ← Back to Frameworks
        </Link>
      </div>
    );
  }

  return (
    <section className="detail-page" id="framework-detail">
      <div className="container">
        <div className="detail-header animate-fade-in-up">
          <div className="detail-breadcrumb">
            <Link href="/">Home</Link>
            <span className="separator">/</span>
            <Link href="/frameworks">Frameworks</Link>
            <span className="separator">/</span>
            <span>{framework.name}</span>
          </div>

          <h1 className="detail-title">{framework.name}</h1>

          <div className="detail-meta">
            <span className="badge" style={{
              background: "var(--accent-blue-dim)",
              color: "var(--accent-blue)",
            }}>
              {framework.category}
            </span>
            {framework.officialUrl && (
              <a
                href={framework.officialUrl}
                target="_blank"
                rel="noopener noreferrer"
                style={{ fontSize: "0.85rem", color: "var(--java-orange)" }}
              >
                🔗 Official Website
              </a>
            )}
          </div>

          <p className="detail-description">{framework.description}</p>
        </div>

        <div className="detail-content">
          <div className="detail-main">
            {/* Overview */}
            {framework.overview && (
              <div className="markdown-content animate-fade-in-up">
                {framework.overview.split("\n").map((line, i) => {
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
                  if (line.trim() === "") return <br key={i} />;
                  return <p key={i}>{line}</p>;
                })}
              </div>
            )}

            {/* Key Features */}
            {framework.keyFeatures && framework.keyFeatures.length > 0 && (
              <>
                <h2 style={{
                  fontFamily: "var(--font-heading)",
                  fontSize: "1.5rem",
                  marginTop: "2rem",
                  marginBottom: "1.5rem",
                }}>
                  ✨ Key Features
                </h2>
                <div className="features-list">
                  {framework.keyFeatures.map((feature, i) => (
                    <div key={i} className="feature-item animate-fade-in-up" style={{ animationDelay: `${i * 0.05}s` }}>
                      <div className="feature-icon">⚡</div>
                      <div className="feature-text">{feature}</div>
                    </div>
                  ))}
                </div>
              </>
            )}

            {/* Getting Started */}
            {framework.gettingStarted && (
              <div className="animate-fade-in-up" style={{ marginTop: "2rem" }}>
                <h2 style={{
                  fontFamily: "var(--font-heading)",
                  fontSize: "1.5rem",
                  marginBottom: "1.5rem",
                }}>
                  🚀 Getting Started
                </h2>
                <div className="markdown-content">
                  {framework.gettingStarted.split("\n").map((line, i) => {
                    if (line.startsWith("### ")) return <h3 key={i}>{line.replace("### ", "")}</h3>;
                    if (line.startsWith("```")) return null;
                    if (line.trim() === "") return <br key={i} />;
                    return <p key={i}>{line}</p>;
                  })}
                </div>
              </div>
            )}

            {/* Code Examples */}
            {framework.codeExamples && framework.codeExamples.length > 0 && (
              <>
                <h2 style={{
                  fontFamily: "var(--font-heading)",
                  fontSize: "1.5rem",
                  marginTop: "2rem",
                  marginBottom: "1.5rem",
                }}>
                  💻 Code Examples
                </h2>
                {framework.codeExamples.map((example, i) => (
                  <div key={i} className="animate-fade-in-up" style={{ animationDelay: `${i * 0.1}s` }}>
                    <CodeBlock example={example} />
                  </div>
                ))}
              </>
            )}
          </div>

          {/* Sidebar */}
          <div className="detail-sidebar">
            <div className="sidebar-card">
              <h3>📋 Quick Info</h3>
              <div className="sidebar-list">
                <div className="sidebar-list-item">Category: {framework.category}</div>
                <div className="sidebar-list-item">Features: {framework.keyFeatures?.length || 0}</div>
                <div className="sidebar-list-item">Code Examples: {framework.codeExamples?.length || 0}</div>
              </div>
            </div>

            {framework.useCases && framework.useCases.length > 0 && (
              <div className="sidebar-card">
                <h3>🎯 Use Cases</h3>
                <div className="sidebar-list">
                  {framework.useCases.map((uc, i) => (
                    <div key={i} className="sidebar-list-item">{uc}</div>
                  ))}
                </div>
              </div>
            )}

            <div className="sidebar-card">
              <h3>🔗 Navigation</h3>
              <div className="sidebar-list">
                <Link href="/frameworks" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  All Frameworks
                </Link>
                <Link href="/topics" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  Core Topics
                </Link>
                <Link href="/interview" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  Interview Prep
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
