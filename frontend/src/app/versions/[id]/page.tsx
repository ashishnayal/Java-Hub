"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import Link from "next/link";
import { api, type JavaVersion } from "@/lib/api";
import CodeBlock from "@/components/CodeBlock";

export default function VersionDetailPage() {
  const params = useParams();
  const [version, setVersion] = useState<JavaVersion | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (params.id) {
      api.getVersion(params.id as string).then((data) => {
        setVersion(data);
        setLoading(false);
      }).catch(() => setLoading(false));
    }
  }, [params.id]);

  if (loading) {
    return (
      <div className="loading-container" style={{ minHeight: "60vh" }}>
        <div className="loading-spinner" />
      </div>
    );
  }

  if (!version) {
    return (
      <div className="empty-state" style={{ minHeight: "60vh" }}>
        <h3>Version not found</h3>
        <Link href="/versions" className="btn btn-secondary" style={{ marginTop: "1rem" }}>
          ← Back to Versions
        </Link>
      </div>
    );
  }

  return (
    <section className="detail-page" id="version-detail">
      <div className="container">
        <div className="detail-header animate-fade-in-up">
          <div className="detail-breadcrumb">
            <Link href="/">Home</Link>
            <span className="separator">/</span>
            <Link href="/versions">Versions</Link>
            <span className="separator">/</span>
            <span>{version.releaseName}</span>
          </div>

          <h1 className="detail-title">{version.releaseName}</h1>

          <div className="detail-meta">
            <span style={{ fontSize: "0.9rem", color: "var(--text-secondary)" }}>
              📅 {version.releaseDate}
            </span>
            {version.lts && <span className="badge badge-lts">Long-Term Support</span>}
            {version.codename && (
              <span style={{ fontSize: "0.85rem", color: "var(--text-muted)" }}>
                Codename: {version.codename}
              </span>
            )}
          </div>

          <p className="detail-description">{version.description}</p>
        </div>

        <div className="detail-content">
          <div className="detail-main">
            {/* Key Features */}
            <h2 style={{ fontFamily: "var(--font-heading)", fontSize: "1.5rem", marginBottom: "1.5rem" }}>
              ✨ Key Features
            </h2>
            <div className="features-list">
              {version.keyFeatures.map((feature, i) => (
                <div key={i} className="feature-item animate-fade-in-up" style={{ animationDelay: `${i * 0.05}s` }}>
                  <div className="feature-icon">⚡</div>
                  <div className="feature-text">{feature}</div>
                </div>
              ))}
            </div>

            {/* Code Examples */}
            {version.codeExamples && version.codeExamples.length > 0 && (
              <>
                <h2 style={{
                  fontFamily: "var(--font-heading)",
                  fontSize: "1.5rem",
                  marginTop: "3rem",
                  marginBottom: "1.5rem",
                }}>
                  💻 Code Examples
                </h2>
                {version.codeExamples.map((example, i) => (
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
                <div className="sidebar-list-item">Version: {version.releaseName}</div>
                <div className="sidebar-list-item">Released: {version.releaseDate}</div>
                <div className="sidebar-list-item">LTS: {version.lts ? "Yes ✅" : "No"}</div>
                {version.codename && (
                  <div className="sidebar-list-item">Codename: {version.codename}</div>
                )}
                <div className="sidebar-list-item">Features: {version.keyFeatures.length}</div>
                <div className="sidebar-list-item">Code Examples: {version.codeExamples?.length || 0}</div>
              </div>
            </div>

            <div className="sidebar-card">
              <h3>🔗 Navigation</h3>
              <div className="sidebar-list">
                <Link href="/versions" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  All Versions
                </Link>
                <Link href="/timeline" className="sidebar-list-item" style={{ cursor: "pointer" }}>
                  Java Timeline
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
