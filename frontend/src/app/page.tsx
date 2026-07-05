"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api, type JavaVersion, type Topic, type Framework } from "@/lib/api";

export default function HomePage() {
  const [versions, setVersions] = useState<JavaVersion[]>([]);
  const [topics, setTopics] = useState<Topic[]>([]);
  const [frameworks, setFrameworks] = useState<Framework[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadData() {
      try {
        const [v, t, f] = await Promise.all([
          api.getVersions(),
          api.getTopics(),
          api.getFrameworks(),
        ]);
        setVersions(v);
        setTopics(t);
        setFrameworks(f);
      } catch (err) {
        console.error("Failed to load data:", err);
      } finally {
        setLoading(false);
      }
    }
    loadData();
  }, []);

  const latestLTS = versions.filter((v) => v.lts).pop();
  const latestVersion = versions[versions.length - 1];

  return (
    <>
      {/* Hero Section */}
      <section className="hero" id="hero-section">
        <div className="container">
          <div className="hero-content">
            <div className="hero-badge">
              <span className="hero-badge-dot" />
              Updated for Java 26 — March 2026
            </div>

            <h1>
              Master <span className="text-gradient">Java</span> from
              <br />
              Discovery to Mastery
            </h1>

            <p className="hero-description">
              Your complete Java knowledge base — from the birth of Oak in 1991
              to Java 26 in 2026. Every version, concept, framework, and
              interview question in one beautiful platform.
            </p>

            <div className="hero-buttons">
              <Link href="/versions" className="btn btn-primary">
                🚀 Explore Java Versions
              </Link>
              <Link href="/interview" className="btn btn-secondary">
                📝 Interview Prep
              </Link>
            </div>

            <div className="stats-grid">
              <div className="stat-card">
                <div className="stat-number">{loading ? "..." : versions.length}</div>
                <div className="stat-label">Java Versions</div>
              </div>
              <div className="stat-card">
                <div className="stat-number">{loading ? "..." : topics.length}</div>
                <div className="stat-label">Core Topics</div>
              </div>
              <div className="stat-card">
                <div className="stat-number">{loading ? "..." : frameworks.length}</div>
                <div className="stat-label">Frameworks</div>
              </div>
              <div className="stat-card">
                <div className="stat-number">30+</div>
                <div className="stat-label">Years of Java</div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Latest Versions */}
      <section className="section" id="latest-versions">
        <div className="container">
          <div className="section-header">
            <div className="section-label">📦 Latest Releases</div>
            <h2 className="section-title">Java Version Highlights</h2>
            <p className="section-description">
              Stay updated with the latest Java releases and their game-changing features.
            </p>
          </div>

          {loading ? (
            <div className="loading-container">
              <div className="loading-spinner" />
            </div>
          ) : (
            <div className="cards-grid stagger-children">
              {versions.slice(-6).reverse().map((v) => (
                <Link
                  href={`/versions/${v.id}`}
                  key={v.id}
                  className="card version-card animate-fade-in-up"
                >
                  <div className="version-card-header">
                    <span className="version-number">
                      {v.versionNumber <= 5 ? v.releaseName.split(" ")[0] : `Java ${v.versionNumber <= 9 ? v.versionNumber - 1 : v.versionNumber - 1}`}
                    </span>
                    <div style={{ display: "flex", gap: "6px" }}>
                      {v.lts && <span className="badge badge-lts">LTS</span>}
                      {v === latestVersion && <span className="badge badge-latest">Latest</span>}
                    </div>
                  </div>
                  <div className="version-name">{v.releaseName}</div>
                  <div className="version-date">{v.releaseDate}</div>
                  <div className="version-features">
                    {v.keyFeatures.slice(0, 3).map((f, i) => (
                      <span key={i} className="feature-tag">{f}</span>
                    ))}
                    {v.keyFeatures.length > 3 && (
                      <span className="feature-tag">+{v.keyFeatures.length - 3} more</span>
                    )}
                  </div>
                </Link>
              ))}
            </div>
          )}

          <div style={{ textAlign: "center", marginTop: "2rem" }}>
            <Link href="/versions" className="btn btn-secondary">
              View All {versions.length} Versions →
            </Link>
          </div>
        </div>
      </section>

      {/* Featured Topics */}
      <section className="section" id="featured-topics">
        <div className="container">
          <div className="section-header">
            <div className="section-label">📚 Core Concepts</div>
            <h2 className="section-title">Essential Java Topics</h2>
            <p className="section-description">
              Deep-dive into the concepts that every Java developer must know.
            </p>
          </div>

          {!loading && (
            <div className="cards-grid cards-grid-3 stagger-children">
              {topics.slice(0, 6).map((t) => {
                const categoryClass =
                  t.category === "Core Java" ? "category-core" :
                  t.category === "OOP" ? "category-oop" :
                  t.category === "Advanced" ? "category-advanced" :
                  t.category.includes("8") ? "category-java8" :
                  "category-java14";

                return (
                  <Link
                    href={`/topics/${t.slug}`}
                    key={t.id}
                    className="card topic-card animate-fade-in-up"
                  >
                    <div className={`topic-card-category ${categoryClass}`}>
                      {t.category}
                    </div>
                    <h3 className="topic-title">{t.title}</h3>
                    <p className="topic-description">
                      {t.description.length > 120
                        ? t.description.slice(0, 120) + "..."
                        : t.description}
                    </p>
                    <div className="topic-footer">
                      <span className={`difficulty-badge difficulty-${t.difficulty.toLowerCase()}`}>
                        {t.difficulty}
                      </span>
                      <span className="topic-qa-count">
                        {t.interviewQuestions?.length || 0} Q&A
                      </span>
                    </div>
                  </Link>
                );
              })}
            </div>
          )}

          <div style={{ textAlign: "center", marginTop: "2rem" }}>
            <Link href="/topics" className="btn btn-secondary">
              Explore All Topics →
            </Link>
          </div>
        </div>
      </section>

      {/* Frameworks */}
      <section className="section" id="frameworks-section">
        <div className="container">
          <div className="section-header">
            <div className="section-label">🛠️ Ecosystem</div>
            <h2 className="section-title">Java Frameworks & Tools</h2>
            <p className="section-description">
              Learn the frameworks that power modern Java applications.
            </p>
          </div>

          {!loading && (
            <div className="cards-grid cards-grid-3 stagger-children">
              {frameworks.slice(0, 6).map((f) => {
                const iconEmoji =
                  f.category === "Web" ? "🌐" :
                  f.category === "ORM" ? "🗄️" :
                  f.category === "Security" ? "🔐" :
                  f.category === "Testing" ? "🧪" :
                  f.category === "Messaging" ? "📨" :
                  f.category === "Build" ? "🔧" :
                  f.category === "Architecture" ? "🏗️" :
                  "📋";

                return (
                  <Link
                    href={`/frameworks/${f.slug}`}
                    key={f.id}
                    className="card framework-card animate-fade-in-up"
                  >
                    <div className={`framework-icon ${f.category.toLowerCase()}`}>
                      {iconEmoji}
                    </div>
                    <h3 className="framework-name">{f.name}</h3>
                    <div className="framework-category">{f.category}</div>
                    <p className="framework-desc">
                      {f.description.length > 150
                        ? f.description.slice(0, 150) + "..."
                        : f.description}
                    </p>
                  </Link>
                );
              })}
            </div>
          )}

          <div style={{ textAlign: "center", marginTop: "2rem" }}>
            <Link href="/frameworks" className="btn btn-secondary">
              View All Frameworks →
            </Link>
          </div>
        </div>
      </section>

      {/* CTA */}
      <section className="section" id="cta-section">
        <div className="container">
          <div style={{
            textAlign: "center",
            padding: "4rem 2rem",
            background: "var(--gradient-card)",
            border: "1px solid var(--border-subtle)",
            borderRadius: "var(--radius-xl)",
            position: "relative",
            overflow: "hidden",
          }}>
            <div style={{
              position: "absolute",
              top: "50%",
              left: "50%",
              transform: "translate(-50%, -50%)",
              width: "500px",
              height: "500px",
              background: "var(--gradient-glow)",
              borderRadius: "50%",
              opacity: 0.5,
            }} />
            <div style={{ position: "relative", zIndex: 1 }}>
              <h2 className="section-title">Ready for Your Java Interview?</h2>
              <p className="section-description" style={{ marginBottom: "2rem" }}>
                Revise every concept, version, and framework in one place.
                Go from refresh to confident in hours, not days.
              </p>
              <Link href="/interview" className="btn btn-primary" style={{ fontSize: "1.1rem", padding: "14px 36px" }}>
                🎯 Start Interview Prep
              </Link>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}
