"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api, type JavaVersion } from "@/lib/api";

export default function VersionsPage() {
  const [versions, setVersions] = useState<JavaVersion[]>([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState("all");

  useEffect(() => {
    api.getVersions().then((data) => {
      setVersions(data);
      setLoading(false);
    }).catch(() => setLoading(false));
  }, []);

  const filtered = filter === "all" ? versions
    : filter === "lts" ? versions.filter((v) => v.lts)
    : filter === "classic" ? versions.filter((v) => v.versionNumber <= 8)
    : filter === "modern" ? versions.filter((v) => v.versionNumber > 8 && v.versionNumber <= 18)
    : versions.filter((v) => v.versionNumber > 18);

  return (
    <section className="section" id="versions-page">
      <div className="container">
        <div className="section-header">
          <div className="section-label">📦 All Releases</div>
          <h1 className="section-title">Java Versions</h1>
          <p className="section-description">
            Every Java release from JDK 1.0 (1996) to Java 26 (2026) — with features, code examples, and release notes.
          </p>
        </div>

        <div className="filter-bar" style={{ justifyContent: "center" }}>
          {[
            { key: "all", label: `All (${versions.length})` },
            { key: "lts", label: "LTS Only" },
            { key: "classic", label: "Classic (1-8)" },
            { key: "modern", label: "Modern (9-17)" },
            { key: "latest", label: "Latest (18+)" },
          ].map((f) => (
            <button
              key={f.key}
              className={`filter-btn ${filter === f.key ? "active" : ""}`}
              onClick={() => setFilter(f.key)}
            >
              {f.label}
            </button>
          ))}
        </div>

        {loading ? (
          <div className="loading-container">
            <div className="loading-spinner" />
          </div>
        ) : (
          <div className="cards-grid stagger-children">
            {filtered.map((v) => (
              <Link
                href={`/versions/${v.id}`}
                key={v.id}
                className="card version-card animate-fade-in-up"
              >
                <div className="version-card-header">
                  <span className="version-number">{v.releaseName.split(" ").pop()}</span>
                  <div style={{ display: "flex", gap: "6px" }}>
                    {v.lts && <span className="badge badge-lts">LTS</span>}
                    {v === versions[versions.length - 1] && (
                      <span className="badge badge-latest">Latest</span>
                    )}
                  </div>
                </div>
                <div className="version-name">{v.releaseName}</div>
                <div className="version-date">
                  📅 {v.releaseDate}
                  {v.codename && ` • ${v.codename}`}
                </div>
                <p style={{ fontSize: "0.85rem", color: "var(--text-secondary)", marginBottom: "12px", lineHeight: 1.6 }}>
                  {v.description.length > 120 ? v.description.slice(0, 120) + "..." : v.description}
                </p>
                <div className="version-features">
                  {v.keyFeatures.slice(0, 4).map((f, i) => (
                    <span key={i} className="feature-tag">{f}</span>
                  ))}
                  {v.keyFeatures.length > 4 && (
                    <span className="feature-tag">+{v.keyFeatures.length - 4}</span>
                  )}
                </div>
              </Link>
            ))}
          </div>
        )}

        {!loading && filtered.length === 0 && (
          <div className="empty-state">
            <h3>No versions match this filter</h3>
            <p>Try a different filter to see results.</p>
          </div>
        )}
      </div>
    </section>
  );
}
