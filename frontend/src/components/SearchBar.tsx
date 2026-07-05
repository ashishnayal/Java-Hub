"use client";

import { useState, useEffect, useRef } from "react";
import { useRouter } from "next/navigation";
import { api, type SearchResults } from "@/lib/api";

export default function SearchBar() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState<SearchResults | null>(null);
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const ref = useRef<HTMLDivElement>(null);
  const router = useRouter();
  const debounceRef = useRef<NodeJS.Timeout>(undefined);

  useEffect(() => {
    function handleClickOutside(e: MouseEvent) {
      if (ref.current && !ref.current.contains(e.target as Node)) {
        setIsOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  useEffect(() => {
    if (debounceRef.current) clearTimeout(debounceRef.current);

    if (query.trim().length < 2) {
      setResults(null);
      setIsOpen(false);
      return;
    }

    debounceRef.current = setTimeout(async () => {
      setIsLoading(true);
      try {
        const data = await api.search(query);
        setResults(data);
        setIsOpen(true);
      } catch {
        setResults(null);
      } finally {
        setIsLoading(false);
      }
    }, 300);

    return () => {
      if (debounceRef.current) clearTimeout(debounceRef.current);
    };
  }, [query]);

  function navigate(path: string) {
    setIsOpen(false);
    setQuery("");
    router.push(path);
  }

  return (
    <div className="search-container" ref={ref}>
      <div className="search-bar">
        <span className="search-icon">🔍</span>
        <input
          type="text"
          placeholder="Search Java concepts..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => results && setIsOpen(true)}
          id="global-search"
        />
        {isLoading && <span className="search-icon" style={{ animation: "spin 1s linear infinite" }}>⏳</span>}
      </div>

      {isOpen && results && (
        <div className="search-results-dropdown">
          {results.versions?.length > 0 && (
            <>
              {results.versions.slice(0, 3).map((v) => (
                <div key={v.id} className="search-result-item" onClick={() => navigate(`/versions/${v.id}`)}>
                  <span className="search-result-type version">Version</span>
                  <div className="search-result-title">{v.releaseName}</div>
                  <div className="search-result-desc">{v.releaseDate} {v.lts ? "• LTS" : ""}</div>
                </div>
              ))}
            </>
          )}
          {results.topics?.length > 0 && (
            <>
              {results.topics.slice(0, 3).map((t) => (
                <div key={t.id} className="search-result-item" onClick={() => navigate(`/topics/${t.slug}`)}>
                  <span className="search-result-type topic">Topic</span>
                  <div className="search-result-title">{t.title}</div>
                  <div className="search-result-desc">{t.category} • {t.difficulty}</div>
                </div>
              ))}
            </>
          )}
          {results.frameworks?.length > 0 && (
            <>
              {results.frameworks.slice(0, 3).map((f) => (
                <div key={f.id} className="search-result-item" onClick={() => navigate(`/frameworks/${f.slug}`)}>
                  <span className="search-result-type framework">Framework</span>
                  <div className="search-result-title">{f.name}</div>
                  <div className="search-result-desc">{f.category}</div>
                </div>
              ))}
            </>
          )}
          {(results.totalResults === 0) && (
            <div className="search-result-item">
              <div className="search-result-title" style={{ color: "var(--text-muted)" }}>No results found</div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
